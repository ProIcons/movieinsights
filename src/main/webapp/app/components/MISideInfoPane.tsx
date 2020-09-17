import './MISideInfoPane.scss'

import variables from './MISideInfoPane.scss'
import React from "react";
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
  MIChartCardFooterField
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
import TranslatableComponent from "app/components/TranslatableComponent";
import {MIValueNumeralFormat} from "app/shared/enumerations/MIValueNumeralFormat";
import {TMDB_IMAGE_URL} from "app/config/constants";

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
        let str = `<div style="text-align: center;font-weight: bold;font-size:20px" class="text-secondary">${this.x}</div><table><tbody>`;
        this.points.forEach((p, i) => {
          const mon = numeral()(p.y).format(format);
          str += `<tr><td style="color: ${variables.colors.split(' ')[p.series.colorIndex]} !important;">${p.series.name}</td><td class="text-secondary">${mon}</td></tr>`
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

class MISideInfoPane extends TranslatableComponent<MISideInfoPaneProps, MISideInfoPaneState> {

  declare header: JSX.Element;

  constructor(props) {
    super(props, "sideInfoPane");
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

  private getTranslateText(key: string) {
    return this.state.movieInsights && !this.props.isShowingPerYear && this.props.movieInsights !== movieInsightsDefaultValue ? this.getTranslation(key) : "";
  }

  private updateData(updateCauseIsYear: boolean) {
    if (this.props.movieInsightsData.yearData?.length > 0 && this.props.movieInsights && this.props.movieInsights !== movieInsightsDefaultValue) {
      let
        vDiffAvg = 0,
        vDiffCount = 0,
        mDiff = 0,
        mDiffCount = 0;

      const _budgetTranslation = this.getPublicTranslation("movie.budget");
      const _revenueTranslation = this.getPublicTranslation("movie.revenue");
      const _ratingTranslation = this.getPublicTranslation("movie.rating");
      const _totalMovieTranslation = this.getTranslation("totalMovies");
      const _moviesWithBudgetTranslation = this.getTranslation("moviesWithBudget");
      const _moviesWithRevenueTranslation = this.getTranslation("moviesWithRevenue");
      const _moviesWithRatingsTranslation  = this.getTranslation("moviesWithRatings");


      const averageRevenueBudgetChartData: SeriesSplineOptions[] = [];
      const totalRevenueBudgetChartData: SeriesSplineOptions[] = [];
      const averageVoteChartData: SeriesSplineOptions[] = [];
      const totalMoviesChartData: SeriesSplineOptions[] = [];
      const avgRevenueLineIndex = averageRevenueBudgetChartData.push({
        type: "spline",
        name: _revenueTranslation,
        data: []
      }) - 1;
      const totalRevenueLineIndex = totalRevenueBudgetChartData.push({
        type: "spline",
        name: _revenueTranslation,
        data: []
      }) - 1;
      const avgBudgetLineIndex = averageRevenueBudgetChartData.push({
        type: "spline",
        name: _budgetTranslation,
        data: []
      }) - 1;
      const totalBudgetLineIndex = totalRevenueBudgetChartData.push({
        type: "spline",
        name: _budgetTranslation,
        data: []
      }) - 1;
      const voteLineIndex = averageVoteChartData.push({
        type: "spline",
        name: _ratingTranslation,
        data: []
      }) - 1;
      const moviesLineIndex = totalMoviesChartData.push({
        type: "spline",
        name: _totalMovieTranslation,
        data: []
      }) - 1;
      const moviesWithBudgetLineIndex = totalMoviesChartData.push({
        type: "spline",
        name: _moviesWithBudgetTranslation,
        data: []
      }) - 1;
      const moviesWithRevenueLineIndex = totalMoviesChartData.push({
        type: "spline",
        name: _moviesWithRevenueTranslation,
        data: []
      }) - 1;
      const moviesWithRatingsLineIndex = totalMoviesChartData.push({
        type: "spline",
        name: _moviesWithRatingsTranslation,
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
            totalMoviesChartData[moviesWithRatingsLineIndex].data.push([data[0], data[8]]);
          });
      }
      this.setState({
        movieDifferencePerYearField: {
          loaded: true,
          value: mDiffCount > 0 ? mDiff / mDiffCount : 0,
          valueFormat: MIValueNumeralFormat.Custom,
          valueFormatCustom: vDiffCount > 0 ? '+0,0.00' : '0',
          subtitle: this.getTranslation("movieProductionDelta")
        },
        averageVotePerYearField: {
          loaded: true,
          value: vDiffCount > 0 ? vDiffAvg / vDiffCount : 0,
          valueFormat: MIValueNumeralFormat.Custom,
          valueFormatCustom: vDiffCount > 0 ? '+0,0.00000000' : '0',
          subtitle: this.getTranslation("movieRatingsDelta")
        },
        averageVoteField: {
          loaded: true,
          value: this.props.movieInsights.averageRating,
          valueFormat: MIValueNumeralFormat.Custom,
          valueFormatCustom: '0,0.00',
          subtitle: this.getTranslation("averageRating")
        },
        averageRevenueBudgetFields: [
          {
            loaded: true,
            value: this.props.movieInsights.averageRevenue,
            subtitle: this.getTranslation("averageRevenue"),
            valueFormat: MIValueNumeralFormat.Money
          },
          {
            loaded: true,
            value: this.props.movieInsights.averageBudget,
            subtitle: this.getTranslation("averageBudget"),
            valueFormat: MIValueNumeralFormat.Money
          }
        ],
        totalMoviesFields: [
          {
            loaded: true,
            value: this.props.movieInsights.totalMovies,
            subtitle: _totalMovieTranslation,
            valueFormat: MIValueNumeralFormat.Integer
          },
          {
            loaded: true,
            value: this.props.movieInsights.totalBudgetMovies,
            subtitle: _moviesWithBudgetTranslation,
            valueFormat: MIValueNumeralFormat.Integer
          },
          {
            loaded: true,
            value: this.props.movieInsights.totalRevenueMovies,
            subtitle: _moviesWithRevenueTranslation,
            valueFormat: MIValueNumeralFormat.Integer
          },
          {
            loaded: true,
            value: this.props.movieInsights.totalRatedMovies,
            subtitle: _moviesWithRatingsTranslation,
            valueFormat: MIValueNumeralFormat.Integer
          }
        ],
        totalRevenueBudgetFields: [
          {
            loaded: true,
            value: this.props.movieInsights.totalRevenue,
            subtitle: this.getTranslation("totalRevenue"),
            valueFormat: MIValueNumeralFormat.Money
          },
          {
            loaded: true,
            value: this.props.movieInsights.totalBudget,
            subtitle: this.getTranslation("totalBudget"),
            valueFormat: MIValueNumeralFormat.Money
          }
        ],
        averageNetProfitField: {
          loaded: true,
          value: this.props.movieInsights.averageRevenue - this.props.movieInsights.averageBudget,
          subtitle: this.getTranslation("averageNetProfit"),
          valueFormatCustom: '+0,0$',
          valueFormat: MIValueNumeralFormat.Custom
        },
        totalNetProfitField: {
          loaded: true,
          value: this.props.movieInsights.totalRevenue - this.props.movieInsights.totalBudget,
          subtitle: this.getTranslation("totalNetProfit"),
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
            {/*<CIcon className={"text-info mi-sideinfo-header-icon"} name={'cil-globe-alt'}/>*/}
            <FontAwesomeIcon className="text-info mi-sideinfo-header-icon"  icon={"globe-americas"}/>
            <div className="text-value-lg font-5xl">{this.getTranslation("worldwide")}</div>
          </div>
        );
      } else if (isBaseEntity(entity)) {
        if (isPerson(entity)) {
          elem2 = (
            <div>
              {entity.profilePath ? (
                  <img  height="250px" style={{borderRadius: "25%"}} onLoad={() => this.setState({logoLoaded: true})}
                       src={`${TMDB_IMAGE_URL}${BASE_IMAGE_PERSON_PATH}${entity.profilePath}`}/>) :
                <div className="text-value-xl" style={{fontSize: "3rem"}}>{entity.name}</div>
              }
              <div className="text-value-lg font-3xl">{entity.name}</div>
            </div>
          );
        } else if (isCompany(entity)) {
          elem2 = (
            <>
              {entity.logoPath ? (
                  <img height="250px" onLoad={() => this.setState({logoLoaded: true})}
                       src={`${TMDB_IMAGE_URL}${BASE_IMAGE_COMPANY_PATH}${entity.logoPath}`}/>) :
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
                <CIcon className={"text-info mi-sideinfo-header-icon"} name={flagName}/>
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
        <div >
          <Skeleton className="alt-skeleton" width={300} height={250}/>
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
              <CRow>&nbsp;</CRow>
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
                    value={this.props.isShowingPerYear ? this.props.year + '' : ''}
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
                    options: defaultChartOptions(this.getTranslateText("averageRevenueAndBudgetPerYear"), MIValueNumeralFormat.Money) //// "Average Revenue/Budget Per Year"
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
                    options: defaultChartOptions(this.getTranslateText("voteAveragePerYear"), MIValueNumeralFormat.Decimal) // this.getText("Vote Average Per Year")
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
                    options: defaultChartOptions(this.getTranslateText("totalRevenueAndBudgetPerYear"), MIValueNumeralFormat.Money) // this.getText("Total Revenue/Budget Per Year")
                  }}
                  fields={this.state.totalRevenueBudgetFields}
                  footer={this.state.totalNetProfitField}
                />
              </CCol>
            </CRow>
            <CRow>
              <CCol className="mi-chart-col" style={{minWidth: "450px"}}>
                <MIChartCard
                  headerIcon={'cil-movie'}
                  headerBackgroundClassName={'bg-gradient-info'}
                  chartProps={{
                    series: this.state.totalMoviesChartData,
                    options: defaultChartOptions(this.getTranslateText("totalMoviesPerYear"), MIValueNumeralFormat.Integer) // this.getText("Total Movies Per Year")
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
