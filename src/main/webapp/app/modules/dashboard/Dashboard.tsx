import './dashboard.scss'
import React, {Component} from "react";
import {CCard, CCardBody, CCardHeader, CCol, CRow} from "@coreui/react";
import {MapChart, MapComponent} from "app/components/charts";
import {connect} from "react-redux";
import {RouteComponentProps} from 'react-router-dom'
import {defaultValue as countryDefaultValue} from 'app/models/IProductionCountry.Model'
import {defaultValue as movieInsightsDefaultValue} from "app/models/IMovieInsights.Model";
import {deepEqual, normalizeText} from "app/utils";
import MISideInfoPane from "app/components/MISideInfoPane";
import MIInsightsPanel from "app/components/MIInsightsPanel";
import {match, matchPath} from "react-router";
import {
  clearView,
  fetch,
  fetchCountryData,
  fetchYear,
  setActive,
  setActiveView,
  setActiveYear,
  Views
} from "app/reducers/dashboard-reducer";
import {IRootState} from "app/shared/reducers";
import BaseMovieInsightsContainerStateManager, {hasYear} from "app/reducers/utils/base-movie-insights-container-state-manager";
import {MIPersonRolePickerProps} from "app/components/MIPersonRolePicker";
import {MovieInsightsPerPersonState} from "app/reducers/movie-insights-per-person-state-manager";
import {MovieInsightsContainerState} from "app/reducers/utils/base-movie-insights-container-state-manager.models";
import animateScrollTo from "animated-scroll-to";
import MISearchBar from "app/components/MISearchBar";
import {ACEntity} from "app/models/AutoComplete.model";
import {EntityType} from "app/models/enumerations/EntityType.enum";
import {AppUtils} from "app/utils/app-utils";
import {ICountryData} from "app/models/ICountryData";
import {MovieInsightsPerCountryState} from "app/reducers/movie-insights-per-country-state-manager";
import {CreditRole, TmdbEntityType} from "app/models/enumerations";
import {LoadingBar} from "react-redux-loading-bar";
import MediaQuery from "react-responsive";


const opts: Highcharts.Options = {
  title: {
    text: undefined,
  },
}


export interface DashboardProps extends StateProps, DispatchProps, RouteComponentProps {
}

export interface DashboardState {
  fetching?: boolean,
  fetchingYear?: boolean,
  fetchingId?: any,
  fetchingEntityType?: TmdbEntityType;
  pathHandled?: boolean,
  path?: string,
  shouldScroll?: boolean,
  countryData: ICountryData[],
}

export class Dashboard extends Component<DashboardProps, DashboardState> {
  private mapComponent = React.createRef<MapComponent>()

  constructor(props) {
    super(props);
    this.state = {
      path: null,
      pathHandled: true,
      shouldScroll: false,
      countryData: [],
    }
  }


  handleGeneralChange = (year?: number) => {
    if (!this.props.rootState.dashboardState.initialized || this.props.rootState.dashboardState.activeView()?.entityType !== EntityType.GENERAL) {
      this.props.setActiveView(Views.MOVIEINSIGHTS_GENERAL);
    }

    const activeView = this.props.rootState.movieInsightsGeneral;

    if (activeView.activeEntity && activeView.activeEntity.movieInsights !== movieInsightsDefaultValue) {
      if (year && activeView.activeYearEntity?.entity === year) {
        this.setState({pathHandled: true, fetching: false, fetchingYear: false});
        return;
      } else if (!year && !activeView.activeYearEntity) {
        this.setState({pathHandled: true, fetching: false, fetchingYear: false});
      }
    }

    this.handleMapSelection(EntityType.GENERAL);

    const isFetching = this.state.fetching;
    const isYearFetching = this.state.fetchingYear;

    if (!isFetching && !isYearFetching) {
      if (activeView.activeEntity && activeView.activeEntity.movieInsights !== movieInsightsDefaultValue) {
        this.props.setActive();
        this.setState({pathHandled: !year})
        if (!year)
          return;
      } else {
        this.props.fetch();
        this.setState({fetching: true})
        return;
      }

      if (year) {
        if (!hasYear(Views.MOVIEINSIGHTS_GENERAL)(year)) {
          this.props.fetchYear(year);
          this.setState({fetchingYear: true})
          return;
        } else {
          this.props.setActiveYear(year);
          this.setState({pathHandled: true})
          return
        }
      }
    }

    if (isFetching && activeView.activeEntity && activeView.activeEntity.movieInsights !== movieInsightsDefaultValue) {
      this.setState({fetching: false, pathHandled: !year});
      return;
    }
    if (isYearFetching && activeView.activeYearEntity && activeView.activeYearEntity.entity === year) {
      this.setState({fetchingYear: false, pathHandled: true});
      return;
    }

  }

  handleGenericChange = (entity: string, id: number, year?: number) => {
    let activeReducer: BaseMovieInsightsContainerStateManager<any, any, any>;
    let activeView: MovieInsightsContainerState<any>;
    let activeEntityType: EntityType;
    switch (entity) {
      case "company":
        activeReducer = Views.MOVIEINSIGHTS_PER_COMPANY;
        activeView = this.props.rootState.movieInsightsPerCompany;
        activeEntityType = EntityType.COMPANY;
        break;
      case "country":
        activeReducer = Views.MOVIEINSIGHTS_PER_COUNTRY;
        activeView = this.props.rootState.movieInsightsPerCountry;
        activeEntityType = EntityType.COUNTRY;
        break;
      case "genre":
        activeReducer = Views.MOVIEINSIGHTS_PER_GENRE;
        activeView = this.props.rootState.movieInsightsPerGenre;
        activeEntityType = EntityType.GENRE;

        break;
      default:
        throw new Error("Invalid View");
    }

    this.handleMapSelection(activeEntityType, id);

    if (!this.props.rootState.dashboardState.initialized || this.props.rootState.dashboardState.activeView()?.entityType !== activeEntityType) {
      this.props.setActiveView(activeReducer);
    }

    if (activeView.activeEntity?.entity?.id === id) {
      if (year && activeView.activeYearEntity?.entity === year) {
        this.setState({pathHandled: true, fetching: false, fetchingYear: false, fetchingId: null})
        return;
      } else if (!year && !activeView.activeYearEntity) {
        this.setState({pathHandled: true, fetching: false, fetchingYear: false, fetchingId: null})
        return;
      }
    }

    const isFetching = this.state.fetching;
    const isYearFetching = this.state.fetchingYear;

    if (!isFetching && !isYearFetching) {
      if (activeView.entitiesCache.filter((c) => c.entity.id === id).length > 0) {
        this.props.setActive(id);
        this.setState({pathHandled: !year});
        if (!year)
          return;
      } else {
        this.props.fetch(id);
        this.setState({fetching: true, fetchingId: id})
        return;
      }

      if (year) {
        if (!hasYear(activeReducer)(year)) {
          this.props.fetchYear(year, id);
          this.setState({fetchingYear: true})
          return;
        } else {
          this.props.setActiveYear(year);
          this.setState({pathHandled: true})
          return;
        }
      }
    }

    if (isFetching && activeView.activeEntity?.entity?.id === this.state.fetchingId) {
      this.setState({fetching: false, pathHandled: !year});
      return;
    }
    if (isYearFetching && activeView.activeYearEntity?.entity === year) {
      this.setState({fetchingYear: false, pathHandled: true});
      return;
    }
  }

  handlePersonChange = (id: number, year?: number, role?: CreditRole) => {
    if (!this.props.rootState.dashboardState.initialized || this.props.rootState.dashboardState.activeView()?.entityType !== EntityType.PERSON) {
      this.props.setActiveView(Views.MOVIEINSIGHTS_PER_PERSON);
    }

    const activeView = this.props.rootState.movieInsightsPerPerson;

    if (activeView._activeEntity?.person?.id === id) {
      if (year && activeView.activeYearEntity?.entity === year) {
        if (role && activeView.activeRole === role || !role) {
          this.setState({pathHandled: true, fetching: false, fetchingYear: false, fetchingId: null})
          return;
        }
      } else if (!year && !activeView.activeYearEntity) {
        if (role && activeView.activeRole === role || !role) {
          this.setState({pathHandled: true, fetching: false, fetchingYear: false, fetchingId: null})
          return;
        }
      }
    }
    this.handleMapSelection(EntityType.PERSON);

    const isFetching = this.state.fetching;
    const isYearFetching = this.state.fetchingYear;

    if (!isFetching && !isYearFetching) {
      if (activeView._entitiesCache.filter((c) => c.person.id === id).length > 0) {
        this.props.setActive(id);
        this.setState({pathHandled: !year && !role});
        if (!year && !role)
          return;
      } else {
        this.props.fetch(id);
        this.setState({fetching: true, fetchingId: id})
        return;
      }

      if (year) {
        if (!hasYear(Views.MOVIEINSIGHTS_PER_PERSON)(year)) {
          this.props.fetchYear(year, id);
          this.setState({fetchingYear: true})
          return;
        } else {
          if (role && activeView.yearRoles[year].includes(role)) {
            this.props.setActiveYear(year, role);
          } else {
            this.props.setActiveYear(year);
          }
          this.setState({pathHandled: true})
          return;
        }
      } else if (role) {
        if (activeView._activeEntity.roles.includes(role)) {
          this.props.setActive(id, role);
          this.setState({pathHandled: true});
          return;
        }
      }
    }

    if (isFetching && activeView.activeEntity?.entity?.id === this.state.fetchingId) {
      this.setState({fetching: false, pathHandled: !year});
      return;
    }
    if (isYearFetching && activeView.activeYearEntity?.entity === year) {
      this.setState({fetchingYear: false, pathHandled: true});
      return;
    }


  }

  handleMapSelection = (type: EntityType, id?: number) => {
    const _map = this.mapComponent.current;

    if (type === EntityType.COUNTRY) {
      if (_map && _map.getSelectedCountry()?._id !== id) {
        _map.selectCountry(id);
      }
    } else {
      if (_map && _map.getSelectedCountry()) {
        _map.unselectCountry();
      }
    }
  }


  scrollElement = () => {
    // Store a 'this' ref, and
    // wait for a paint before running scrollHeight dependent code.
    // eslint-disable-next-line @typescript-eslint/no-this-alias


    window.requestAnimationFrame(() => {
      setTimeout(() => {
        void animateScrollTo(0, {
          minDuration: 1400
        });
      }, 750);
    });


  }
  handleViewChange = () => {
    const path = this.props.location.pathname;

    if (this.state.path !== path || !this.state.pathHandled) {
      if (!this.state.pathHandled) {
        let pathMatch: match;
        if ((pathMatch = matchPath(path, "/app/:entity(country|company|genre)/:id-:name/:year(\\d{4})?"))) {
          this.handleGenericChange(pathMatch.params['entity'], +pathMatch.params['id'], +pathMatch.params['year']);
        } else if ((pathMatch = matchPath(path, "/app/person/:id-:name/:role([A-z]+)?/:year(\\d{4})?"))) {
          this.handlePersonChange(+pathMatch.params['id'], +pathMatch.params['year'], pathMatch.params['role']);
        } else if ((pathMatch = matchPath(path, "/app/:general(general)?/:year(\\d{4})?"))) {
          this.handleGeneralChange(+pathMatch.params['year']);
        }
        this.scrollElement();
        if (this.state.path !== path)
          this.setState({path});
      }
      if (this.state.path !== path) {
        this.setState({pathHandled: false});
      }
    }

  }

  componentDidUpdate(prevProps: Readonly<DashboardProps>, prevState: Readonly<DashboardState>, snapshot?: any) {
    this.handleViewChange();

    const activeView = this.props.rootState.dashboardState.activeView();

    if (!deepEqual(this.state.countryData, this.props.rootState.dashboardState.countryData)) {
      this.setState({countryData: this.props.rootState.dashboardState.countryData});
    }

    if (activeView.errorMessage) {
      this.props.history.push("/404");
    }
  }

  componentDidMount() {
    this.props.fetchCountryData();
  }

  countrySelected = (data: ICountryData) => {
    this.props.history.push(`/app/country/${data._id}-${normalizeText(data.name)}`)
  }
  countryUnselected = (data: ICountryData) => {
    this.props.history.push(`/app`)
  }

  yearSelected = (year: number) => {
    const activeView = this.props.rootState.dashboardState.activeView();
    const activeEntity = this.props.rootState.dashboardState.activeView().activeEntity;
    let role = null;
    if (activeView.entityType === EntityType.PERSON) {
      const _activeView = activeView as MovieInsightsPerPersonState;
      role = _activeView.activeRole;
    }
    this.props.history.push(AppUtils.generateNavigationLink(activeEntity.entity, role, year))
  }
  yearUnselected = () => {
    const activeEntity = this.props.rootState.dashboardState.activeView().activeEntity;

    if (activeEntity.entity) {
      this.props.history.push(AppUtils.generateNavigationLink(activeEntity.entity));
    } else {
      this.props.history.push(`/app`);
    }
  }

  private getCountryData: () => ICountryData[] = () => {
    return this.state.countryData;
  }

  private getSelectedCountry = () => {
    const data = this.getCountryData();
    if (this.props.rootState.dashboardState.activeView().entityType === EntityType.COUNTRY) {
      const activeView: MovieInsightsPerCountryState = this.props.rootState.dashboardState.activeView();
      if (activeView.activeEntity.entity !== countryDefaultValue) {
        return data.filter((country) => country?.iso31661 === activeView.activeEntity?.entity?.iso31661)[0];
      }
    }
    return null;
  }

  private onCreditSelect = (credit: CreditRole) => {
    const activeView = this.props.rootState.dashboardState.activeView() as MovieInsightsPerPersonState;
    this.props.history.push(AppUtils.generateNavigationLink(activeView._activeEntity.person, credit, activeView.isPerYear ? activeView.activeYearEntity.entity : null));
  }

  private onSearchSelected = (val: ACEntity) => {
    this.props.history.push(AppUtils.generateNavigationLink(val, null, null, val.i));
    // eslint-disable-next-line no-console
  }

  render() {
    const activeView = this.props.rootState.dashboardState.activeView();
    let creditSelector: MIPersonRolePickerProps = null;
    if (activeView.entityType === EntityType.PERSON) {
      const _activeView = activeView as MovieInsightsPerPersonState;
      creditSelector = {
        selected: _activeView.activeRole,
        roles: _activeView.activeRoles,
        onSelect: this.onCreditSelect
      }
    }

    return (
      <>
        <CCard>
          <CCardHeader>
            <CRow className="justify-content-center">
              <MISearchBar onSelected={this.onSearchSelected}/>
            </CRow>
            <LoadingBar loading={this.props.rootState.loadingBar.app} scope={"app"} showFastActions
                        className="loading-bar"/>
          </CCardHeader>
        </CCard>
        <CCard>
          <CCardBody>
            <CRow style={{height: "min-content"}}>
              <MediaQuery minDeviceWidth={"675px"}>
                <CCol className="mi-map-col">
                  <MapChart
                    ref={this.mapComponent}
                    options={opts}
                    countryData={this.getCountryData()}
                    countrySelected={this.countrySelected}
                    countryUnselected={this.countryUnselected}
                    defaultSelectedCountry={this.getSelectedCountry()}
                  />
                </CCol>
                <div className="c-vr d-md-none d-lg-block">&nbsp;</div>
              </MediaQuery>
              <CCol className="text-center mi-side-info-col">
                <MISideInfoPane
                  yearSelected={this.yearSelected}
                  yearUnselected={this.yearUnselected}
                  movieInsights={activeView.activeMovieInsights}
                  movieInsightsData={activeView.activeEntity}
                  isShowingPerYear={activeView.isPerYear}
                  year={activeView.isPerYear?activeView.activeYearEntity.entity:null}
                  creditSelector={creditSelector}
                />
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
        <hr/>
        <MIInsightsPanel movieInsights={activeView.activeMovieInsights}/>
      </>
    );
  }

  /* private selectCountry(data: Data[]) {
     let max = 0;
     let maxObj: Data;
     for (const dataObj of data) {
       if (dataObj.value > max) {
         max = dataObj.value;
         maxObj = dataObj;
       }
     }
     return maxObj;
   }*/

}

const mapStateToProps = storeState => ({
  rootState: storeState as IRootState
});
type StateProps = ReturnType<typeof mapStateToProps>
const mapDispatchToProps = {
  setActiveView,
  setActive,
  setActiveYear,
  fetch,
  fetchYear,
  clearView,
  fetchCountryData
};
type DispatchProps = typeof mapDispatchToProps;
export default connect(mapStateToProps, mapDispatchToProps)(Dashboard);
