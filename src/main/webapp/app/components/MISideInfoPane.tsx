import './MISideInfoPane.scss'

import variables from './MISideInfoPane.scss'
import React, {Component} from "react";
import CIcon from "@coreui/icons-react";
import {CCard, CCardHeader, CCol, CRow} from "@coreui/react";
import {connect} from "react-redux";
import {SeriesSplineOptions} from "highcharts";
import {numeral} from "app/utils";

import deepEqual from 'fast-deep-equal';

import MIChartCard, {
  fieldDefaults,
  footerFieldDefaults,
  MIChartCardField,
  MIChartCardFooterField,
  MIValueNumeralFormat
} from "app/components/cards/MIChartCard";
import MIYearPicker from "app/components/MIYearPicker";
import Skeleton from "react-loading-skeleton";
import MIPersonRolePicker, {MIPersonRolePickerProps} from "app/components/MIPersonRolePicker";
import {isGenre} from "app/models/IGenre.Model";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {NavLink} from "react-router-dom";
import MIDivider from "app/components/MIDivider";
import {defaultValue as movieInsightsDefaultValue, IMovieInsights} from "app/models/IMovieInsights.Model";
import {BaseMovieInsightsPerYearContainer} from "app/models/BaseMovieInsights.Model";
import {BaseEntity, isBaseEntity} from "app/models/BaseEntity.Model";
import {TmdbEntityType} from "app/models/enumerations";
import {isPerson} from "app/models/IPerson.Model";
import {isCompany} from "app/models/IProductionCompany.Model";
import {isCountry} from "app/models/IProductionCountry.Model";

const BASE_IMAGE_URL = "https://image.tmdb.org/t/p"
const BASE_IMAGE_PERSON_PATH = "/w185"
const BASE_IMAGE_COMPANY_PATH = "/w300"
const defaultChartOptions = (title: string, format: MIValueNumeralFormat) => {
  return {
    title: {
      text: title
    },
    tooltip: {
      useHTML: true,
      formatter() {
        let str = `<div style="text-align: center;font-weight: bold;font-size:20px">${this.x}</div><table><tbody>`;
        this.points.forEach((p, i) => {
          const mon = numeral()(p.y).format(format);
          str += `<tr><td style="color: ${variables.colors.split(' ')[p.series.colorIndex]} !important;">${p.series.name}</td><td>${mon}</td></tr>`
        })
        str += `</tbody></table>`
        return str;
      }
    },
  }
}


export interface MISideInfoPaneState {
  averageRevenueBudgetFields: MIChartCardField[];
  totalRevenueBudgetFields: MIChartCardField[];
  totalMoviesFields: MIChartCardField[];
  averageNetProfitField: MIChartCardFooterField;
  totalNetProfitField: MIChartCardFooterField;
  averageVoteField: MIChartCardField;
  averageVotePerYearField: MIChartCardFooterField;
  movieDifferencePerYearField: MIChartCardFooterField;
  movieInsights: IMovieInsights;
  movieInsightsData?: BaseMovieInsightsPerYearContainer<any>;
  isShowingPerYear: boolean;
  averageRevenueBudgetChartData: SeriesSplineOptions[];
  totalRevenueBudgetChartData: SeriesSplineOptions[];
  averageVoteChartData: SeriesSplineOptions[];
  totalMoviesChartData: SeriesSplineOptions[];
  logoLoaded: boolean;
  isReset: boolean;
  updateCauseIsYear: boolean;
  header: JSX.Element;

}

export interface MISideInfoPaneProps extends StateProps {
  movieInsights: IMovieInsights;
  yearSelected: (year: number) => void;
  yearUnselected: () => void;
  isShowingPerYear: boolean;
  movieInsightsData?: BaseMovieInsightsPerYearContainer<any>;
  creditSelector: MIPersonRolePickerProps;
  year: number;
}

export interface MISideInfoPaneDataGroup {
  entity: BaseEntity | number;
  movieInsights: IMovieInsights;
  isPerYear: boolean;
  yearData: number[][];
  entityType: TmdbEntityType;

}

class MISideInfoPane extends Component<MISideInfoPaneProps, MISideInfoPaneState> {

  declare header: JSX.Element;

  constructor(props) {
    super(props);
    this.state = this.defaultState();
  }

  defaultState = () => {
    return {
      isShowingPerYear: this.props.isShowingPerYear,
      movieInsights: this.props.movieInsights,
      movieInsightsData: this.props.movieInsightsData,
      averageNetProfitField: footerFieldDefaults(),
      averageVoteField: fieldDefaults(),
      averageRevenueBudgetFields: [fieldDefaults(), fieldDefaults()],
      averageVotePerYearField: footerFieldDefaults(),
      averageRevenueBudgetChartData: [],
      averageVoteChartData: [],
      totalRevenueBudgetFields: [fieldDefaults(), fieldDefaults()],
      totalMoviesFields: [fieldDefaults(), fieldDefaults(), fieldDefaults()],
      totalNetProfitField: footerFieldDefaults(),
      totalRevenueBudgetChartData: [],
      totalMoviesChartData: [],
      movieDifferencePerYearField: footerFieldDefaults(),
      logoLoaded: false,
      isReset: true,
      updateCauseIsYear: false,
      header: <></>,
    };
  };

  reset = () => {
    this.setState(this.defaultState());
  }

  componentDidMount() {
    this.updateData(false);
  }

  componentDidUpdate(prevProps: Readonly<MISideInfoPaneProps>, prevState: Readonly<MISideInfoPaneState>, snapshot?: any) {
    const movieInsightsDataIsSame = deepEqual(this.state.movieInsightsData, this.props.movieInsightsData);
    if (!movieInsightsDataIsSame || !deepEqual(this.state.movieInsights, this.props.movieInsights)) {
      this.setState({
        isReset: false,
        movieInsightsData: this.props.movieInsightsData,
        movieInsights: this.props.movieInsights,
        updateCauseIsYear: movieInsightsDataIsSame
      })
      this.updateData(movieInsightsDataIsSame);
    }
  }

  private getText(text: string) {
    return this.state.movieInsights && !this.props.isShowingPerYear && this.props.movieInsights !== movieInsightsDefaultValue ? text : "";
  }

  private updateData(updateCauseIsYear: boolean) {
    if (this.props.movieInsightsData.yearData?.length > 0 && this.props.movieInsights && this.props.movieInsights !== movieInsightsDefaultValue) {
      let
        vDiffAvg = 0,
        vDiffCount = 0,
        mDiff = 0,
        mDiffCount = 0;
      const averageRevenueBudgetChartData: SeriesSplineOptions[] = [];
      const totalRevenueBudgetChartData: SeriesSplineOptions[] = [];
      const averageVoteChartData: SeriesSplineOptions[] = [];
      const totalMoviesChartData: SeriesSplineOptions[] = [];
      const avgRevenueLineIndex = averageRevenueBudgetChartData.push({
        type: "spline",
        name: "Revenue",
        data: []
      }) - 1;
      const totalRevenueLineIndex = totalRevenueBudgetChartData.push({
        type: "spline",
        name: "Revenue",
        data: []
      }) - 1;
      const avgBudgetLineIndex = averageRevenueBudgetChartData.push({
        type: "spline",
        name: "Budget",
        data: []
      }) - 1;
      const totalBudgetLineIndex = totalRevenueBudgetChartData.push({
        type: "spline",
        name: "Budget",
        data: []
      }) - 1;
      const voteLineIndex = averageVoteChartData.push({
        type: "spline",
        name: "Vote Average",
        data: []
      }) - 1;
      const moviesLineIndex = totalMoviesChartData.push({
        type: "spline",
        name: "Total Movies",
        data: []
      }) - 1;
      const moviesWithBudgetLineIndex = totalMoviesChartData.push({
        type: "spline",
        name: "Movies with Budget",
        data: []
      }) - 1;
      const moviesWithRevenueLineIndex = totalMoviesChartData.push({
        type: "spline",
        name: "Movies with Revenue",
        data: []
      }) - 1;

      /*
        0  Year

        1  TotalMovies

        2  TotalBudget
        3  TotalBudgetMovies
        4  AverageBudget

        5  TotalRevenue
        6  TotalRevenueMovies
        7  AverageRevenue

        8  TotalRatedMovies
        9  AverageRating

        10 TotalActors
        11 AverageActorCount

        12 TotalDirectors
        13 AverageDirectorCount

        14 TotalProducers
        15 AverageProducerCount

        16 TotalWriters
        17 AverageWriterCount

        18 TotalGenres
        19 AverageGenreCount

        20 TotalProductionCompanies
        21 AverageProductionCompanyCount

        22 TotalProductionCountries
        23 AverageProductionCountryCount

      */

      if (!this.props.isShowingPerYear) {
        this.props.movieInsightsData.yearData
          .sort((data1, data2) => data1[0] - data2[0])
          .forEach((data, index) => {
            if (this.props.movieInsightsData.yearData[index - 1]) {
              vDiffAvg += data[9] - this.props.movieInsightsData.yearData[index - 1][9];
              mDiff += data[1] - this.props.movieInsightsData.yearData[index - 1][1];
              vDiffCount++;
              mDiffCount++;
            }
            averageRevenueBudgetChartData[avgRevenueLineIndex].data.push([data[0], data[7]]);
            totalRevenueBudgetChartData[totalRevenueLineIndex].data.push([data[0], data[5]]);
            averageRevenueBudgetChartData[avgBudgetLineIndex].data.push([data[0], data[4]]);
            totalRevenueBudgetChartData[totalBudgetLineIndex].data.push([data[0], data[2]]);
            averageVoteChartData[voteLineIndex].data.push([data[0], data[9]]);
            totalMoviesChartData[moviesLineIndex].data.push([data[0], data[1]]);
            totalMoviesChartData[moviesWithRevenueLineIndex].data.push([data[0], data[6]]);
            totalMoviesChartData[moviesWithBudgetLineIndex].data.push([data[0], data[3]]);
          });
      }
      this.setState({
        movieDifferencePerYearField: {
          loaded: true,
          value: mDiffCount > 0 ? mDiff / mDiffCount : 0,
          valueFormat: MIValueNumeralFormat.Custom,
          valueFormatCustom: vDiffCount > 0 ? '+0,0.00' : '0',
          subtitle: "Movie Production Delta per year"
        },
        averageVotePerYearField: {
          loaded: true,
          value: vDiffCount > 0 ? vDiffAvg / vDiffCount : 0,
          valueFormat: MIValueNumeralFormat.Custom,
          valueFormatCustom: vDiffCount > 0 ? '+0,0.00000000' : '0',
          subtitle: "Vote Delta per year"
        },
        averageVoteField: {
          loaded: true,
          value: this.props.movieInsights.averageRating,
          valueFormat: MIValueNumeralFormat.Custom,
          valueFormatCustom: '0,0.00',
          subtitle: "Vote Average"
        },
        averageRevenueBudgetFields: [
          {
            loaded: true,
            value: this.props.movieInsights.averageRevenue,
            subtitle: "AVG Revenue",
            valueFormat: MIValueNumeralFormat.Money
          },
          {
            loaded: true,
            value: this.props.movieInsights.averageBudget,
            subtitle: "AVG Budget",
            valueFormat: MIValueNumeralFormat.Money
          }
        ],
        totalMoviesFields: [
          {
            loaded: true,
            value: this.props.movieInsights.totalMovies,
            subtitle: "Movies",
            valueFormat: MIValueNumeralFormat.Integer
          },
          {
            loaded: true,
            value: this.props.movieInsights.totalBudgetMovies,
            subtitle: "Movies with Budget",
            valueFormat: MIValueNumeralFormat.Integer
          },
          {
            loaded: true,
            value: this.props.movieInsights.totalRevenueMovies,
            subtitle: "Movies with Revenue",
            valueFormat: MIValueNumeralFormat.Integer
          },
        ],
        totalRevenueBudgetFields: [
          {
            loaded: true,
            value: this.props.movieInsights.totalRevenue,
            subtitle: "Total Revenue",
            valueFormat: MIValueNumeralFormat.Money
          },
          {
            loaded: true,
            value: this.props.movieInsights.totalBudget,
            subtitle: "Total Budget",
            valueFormat: MIValueNumeralFormat.Money
          }
        ],
        averageNetProfitField: {
          loaded: true,
          value: this.props.movieInsights.averageRevenue - this.props.movieInsights.averageBudget,
          subtitle: "Average net profit per year",
          valueFormatCustom: '+0,0$',
          valueFormat: MIValueNumeralFormat.Custom
        },
        totalNetProfitField: {
          loaded: true,
          value: this.props.movieInsights.totalRevenue - this.props.movieInsights.totalBudget,
          subtitle: "Total Net Profit",
          valueFormatCustom: '+0,0$',
          valueFormat: MIValueNumeralFormat.Custom
        },
        averageRevenueBudgetChartData,
        averageVoteChartData,
        totalRevenueBudgetChartData,
        totalMoviesChartData

      });
    } else {
      this.setState(this.defaultState());
    }
  }

  private yearBoundsValidation = (currentYear: number): boolean => {
    // eslint-disable-next-line no-console
    if (this.props.movieInsightsData.yearData) {
      return this.props.movieInsightsData.yearData.filter(data => data[0] === currentYear).length > 0;
    }
    return false;
  }

  private isLogoImage: () => boolean = () => {

    /*const entity = this.props.movieInsightsData.entity;
    if (isCountry(entity) || isGenre(entity)) {

    }*/
    return false;
  }

  private getHeader = () => {
    const entity = this.props.movieInsightsData.entity;
    let elem;
    let elem2;
    void new Promise(() => {
      setTimeout(() => {
        if (!this.state.logoLoaded)
          this.setState({logoLoaded: true});
      }, 2500)
    });

    if (this.props.movieInsights !== movieInsightsDefaultValue) {
      if (entity === undefined) {
        if (!this.state.logoLoaded)
          void new Promise(() => {
            setTimeout(() => {
              this.setState({logoLoaded: true});
            }, 10);
          });
        elem2 = (
          <div>
            <CIcon className={"text-info"} name={'cil-globe-alt'} size="8xl"/>
            <div className="text-value-lg font-5xl">Worldwide</div>
          </div>
        );
      } else if (isBaseEntity(entity)) {
        if (isPerson(entity)) {
          elem2 = (
            <div>
              {entity.profilePath ? (
                  <img style={{borderRadius: "25%"}} onLoad={() => this.setState({logoLoaded: true})}
                       src={`${BASE_IMAGE_URL}${BASE_IMAGE_PERSON_PATH}${entity.profilePath}`}/>) :
                <div className="text-value-xl" style={{fontSize: "3rem"}}>{entity.name}</div>
              }
              <div className="text-value-lg font-3xl">{entity.name}</div>
            </div>
          );
        } else if (isCompany(entity)) {
          elem2 = (
            <>
              {entity.logoPath ? (
                  <img onLoad={() => this.setState({logoLoaded: true})}
                       src={`${BASE_IMAGE_URL}${BASE_IMAGE_COMPANY_PATH}${entity.logoPath}`}/>) :
                <div className="text-value-xl" style={{fontSize: "3rem"}}>{entity.name}</div>
              }
            </>
          );

        } else if (isCountry(entity)) {
          const flagName = 'cif' + entity.iso31661[0].toUpperCase() + entity.iso31661.substring(1).toLowerCase();
          if (!this.state.logoLoaded)
            void new Promise(() => {
              setTimeout(() => {
                this.setState({logoLoaded: true});
              }, 10);
            });
          elem2 = (
            <div>
              {flagName !== "cif" ? (
                <CIcon className={"text-info"} name={flagName} size="8xl"/>
              ) : null
              }
              <div className="text-value-lg font-5xl">{entity.name}</div>
            </div>
          );
        } else if (isGenre(entity)) {
          if (!this.state.logoLoaded)
            void new Promise(() => {
              setTimeout(() => {
                this.setState({logoLoaded: true});
              }, 10);
            });
          elem2 = (
            <div>
              <FontAwesomeIcon className="text-info" icon={"theater-masks"} size={"8x"}/>
              <div className="text-value-lg font-5xl">{entity.name}</div>
            </div>
          );
        }
      }
    }
    if (this.props.movieInsights === movieInsightsDefaultValue || !this.state.logoLoaded) {
      elem = (
        <div style={{position: "absolute"}}>
          <Skeleton className="alt-skeleton" width={300} height={200}/>
        </div>
      )
    }
    const header = <>{!this.state.logoLoaded ? elem : null}{elem2}</>;
    if (header === this.header)
      return this.header;
    else {
      this.header = header;
      return header;
    }

  }

  render() {
    return (
      <>
        <CCard className="mi-side-info-card">
          <CCardHeader className="bg-gradient-secondary text-dark">
            {this.props.movieInsightsData.entity !== undefined ? (
              <div className="mi-sideinfo-reset text-dark"><NavLink to={"/app"}><CIcon size={"xl"}
                                                                                       name={"cil-x"}/></NavLink>
              </div>) : null}
            <CCol>
              <CRow>
                <CCol
                  className="text-center justify-content-center align-middle"
                  style={{minHeight: "200px", display: "flex", alignItems: "center", flexWrap: "wrap"}}
                >
                  {this.getHeader()}
                </CCol>
              </CRow>
              {this.props.creditSelector ? (
                <>
                  <CRow>
                    <CCol>
                      <MIPersonRolePicker
                        selected={this.props.creditSelector.selected}
                        roles={this.props.creditSelector.roles}
                        onSelect={this.props.creditSelector.onSelect}
                      />
                    </CCol>
                  </CRow>
                  <hr/>
                </>
              ) : null}
              <CRow className="justify-content-center text-white">
                <CCol className="justify-content-center">
                  <MIYearPicker
                    yearValidation={this.yearBoundsValidation}
                    onYearSelect={this.props.yearSelected}
                    onYearUnselect={this.props.yearUnselected}
                    inputProps={{
                      className: `form-control bg-gradient-dark `
                    }}
                    value={this.props.isShowingPerYear?this.props.year+'':''}
                  />
                </CCol>
              </CRow>
            </CCol>
          </CCardHeader>
          <CCardHeader>
            <CRow>
              <CCol style={{minWidth: "360px"}}>
                <MIChartCard
                  headerIcon={'cis-euro'}
                  headerBackgroundClassName={'bg-gradient-info'}
                  chartProps={{
                    series: this.state.averageRevenueBudgetChartData,
                    options: defaultChartOptions(this.getText("Average Revenue/Budget Per Year"), MIValueNumeralFormat.Money)
                  }}
                  fields={this.state.averageRevenueBudgetFields}
                  footer={this.state.averageNetProfitField}
                />
              </CCol>
              <MIDivider spacing={2}/>
              <CCol style={{minWidth: "225px"}}>
                <MIChartCard
                  headerIcon={'cil-pen'}
                  headerBackgroundClassName={'bg-gradient-info'}
                  chartProps={{
                    series: this.state.averageVoteChartData,
                    options: defaultChartOptions(this.getText("Vote Average Per Year"), MIValueNumeralFormat.Decimal)
                  }}
                  fields={[this.state.averageVoteField]}
                  footer={this.state.averageVotePerYearField}
                />
              </CCol>
            </CRow>
            <CRow>
              <CCol className="mi-chart-col-revenue">
                <MIChartCard
                  headerIcon={'cis-euro'}
                  headerBackgroundClassName={'bg-gradient-info'}
                  chartProps={{
                    series: this.state.totalRevenueBudgetChartData,
                    options: defaultChartOptions(this.getText("Total Revenue/Budget Per Year"), MIValueNumeralFormat.Money)
                  }}
                  fields={this.state.totalRevenueBudgetFields}
                  footer={this.state.totalNetProfitField}
                />
              </CCol>
              <MIDivider spacing={2}/>
              <CCol className="mi-chart-col" style={{minWidth: "450px"}}>
                <MIChartCard
                  headerIcon={'cil-movie'}
                  headerBackgroundClassName={'bg-gradient-info'}
                  chartProps={{
                    series: this.state.totalMoviesChartData,
                    options: defaultChartOptions(this.getText("Total Movies Per Year"), MIValueNumeralFormat.Integer)
                  }}
                  fields={this.state.totalMoviesFields}
                  footer={this.state.movieDifferencePerYearField}
                />
              </CCol>
            </CRow>
          </CCardHeader>
        </CCard>
      </>
    );
  }
}

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});
type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(MISideInfoPane);
