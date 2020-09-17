import './MIInsightsPanel.scss'
import React, {Component} from "react";
import {CCard, CCardBody, CCardHeader, CCol, CRow} from "@coreui/react";

import MIMovieCard, {MIMovieCardType} from "app/components/cards/MIMovieCard";
import MIDivider from "app/components/MIDivider";
import MIPersonCard from "app/components/cards/MIPersonCard";
import MICompanyCard from "app/components/cards/MICompanyCard";
import MICountryCard from "app/components/cards/MICountryCard";
import MIGenreCard from "app/components/cards/MIGenreCard";
import {defaultValue as movieInsightsDefaultValue, IMovieInsights} from "app/models/IMovieInsights.Model";
import {EntityType} from "app/models/enumerations/EntityType.enum";
import {CreditRole} from "app/models/enumerations";
import {MIValueType} from "app/shared/enumerations/MIValueType";


export interface MIInsightsPanelState {
  movieInsights: IMovieInsights;
  parentType: EntityType;
  activeRole: CreditRole;
}

export interface MIInsightsPanelProps extends MIInsightsPanelState {

}

export class MIInsightsPanel extends Component<MIInsightsPanelProps, any> {

  render() : JSX.Element {
    const movieInsights = this.props.movieInsights ? this.props.movieInsights : movieInsightsDefaultValue;

    return (

      <CRow>
        <CCol className="entityCol">
          <CCard>
            <CCardHeader>
              <h2 className="text-center">Movies</h2>
            </CCardHeader>
            <CCardBody>
              <h4 className="text-center text-muted">Rating</h4>
              <hr/>
              <CRow className="justify-content-center ">
                <MIMovieCard
                  entity={movieInsights.highestRatedLogarithmicMovie}
                  type={MIMovieCardType.MostVote}
                />
                <MIDivider spacing={10}/>
                <MIMovieCard
                  entity={movieInsights.lowestRatedMovie}
                  type={MIMovieCardType.LeastVote}
                />
              </CRow>
              <h4 className="text-center text-muted">Revenue</h4>
              <hr/>
              <CRow className="justify-content-center">
                <MIMovieCard
                  entity={movieInsights.highestRevenueMovie}
                  type={MIMovieCardType.MostRevenue}
                />
                <MIMovieCard
                  entity={movieInsights.lowestRevenueMovie}
                  type={MIMovieCardType.LeastRevenue}
                />
              </CRow>
              <h4 className="text-center text-muted">Budget</h4>
              <hr/>
              <CRow className="justify-content-center">

                <MIMovieCard
                  entity={movieInsights.highestBudgetMovie}
                  type={MIMovieCardType.MostBudget}
                />
                <MIMovieCard
                  entity={movieInsights.lowestBudgetMovie}
                  type={MIMovieCardType.LeastBudget}
                />
              </CRow>
            </CCardBody>
          </CCard>
        </CCol>
        <CCol  className="entityCol">
          <CCard>
            <CCardHeader>
              <h2 className="text-center">Credits</h2>
            </CCardHeader>
            <CCardBody>
              <h4 className="text-center text-muted">Actors</h4>
              <hr/>
              <CRow className="justify-content-center">
                <MIPersonCard
                  entity={movieInsights.mostPopularActor}
                  movieCount={movieInsights.mostPopularActorMovieCount}
                  type={CreditRole.ACTOR}
                  valueType={MIValueType.Most}
                  isCooperative={this.props.parentType === EntityType.PERSON && this.props.activeRole === CreditRole.ACTOR}
                />
                <MIDivider/>
                <MIPersonCard
                  entity={movieInsights.leastPopularActor}
                  movieCount={movieInsights.leastPopularActorMovieCount}
                  type={CreditRole.ACTOR}
                  valueType={MIValueType.Least}
                  isCooperative={this.props.parentType === EntityType.PERSON && this.props.activeRole === CreditRole.ACTOR}
                />
              </CRow>
              <h4 className="text-center text-muted">Directors</h4>
              <hr/>
              <CRow className="justify-content-center">
                <MIPersonCard
                  entity={movieInsights.mostPopularDirector}
                  movieCount={movieInsights.mostPopularDirectorMovieCount}
                  type={CreditRole.DIRECTOR}
                  valueType={MIValueType.Most}
                  isCooperative={this.props.parentType === EntityType.PERSON && this.props.activeRole === CreditRole.DIRECTOR}
                />
                <MIDivider/>
                <MIPersonCard
                  entity={movieInsights.leastPopularDirector}
                  movieCount={movieInsights.leastPopularDirectorMovieCount}
                  type={CreditRole.DIRECTOR}
                  valueType={MIValueType.Least}
                  isCooperative={this.props.parentType === EntityType.PERSON && this.props.activeRole === CreditRole.DIRECTOR}
                />
              </CRow>
              <h4 className="text-center text-muted">Producers</h4>
              <hr/>
              <CRow className="justify-content-center">
                <MIPersonCard
                  entity={movieInsights.mostPopularProducer}
                  movieCount={movieInsights.mostPopularProducerMovieCount}
                  type={CreditRole.PRODUCER}
                  valueType={MIValueType.Most}
                  isCooperative={this.props.parentType === EntityType.PERSON && this.props.activeRole === CreditRole.PRODUCER}
                />
                <MIDivider/>
                <MIPersonCard
                  entity={movieInsights.leastPopularProducer}
                  movieCount={movieInsights.leastPopularProducerMovieCount}
                  type={CreditRole.PRODUCER}
                  valueType={MIValueType.Least}
                  isCooperative={this.props.parentType === EntityType.PERSON && this.props.activeRole === CreditRole.PRODUCER}
                />
              </CRow>
              <h4 className="text-center text-muted">Writers</h4>
              <hr/>
              <CRow className="justify-content-center">
                <MIPersonCard
                  entity={movieInsights.mostPopularWriter}
                  movieCount={movieInsights.mostPopularWriterMovieCount}
                  type={CreditRole.WRITER}
                  valueType={MIValueType.Most}
                  isCooperative={this.props.parentType === EntityType.PERSON && this.props.activeRole === CreditRole.WRITER}
                />
                <MIDivider/>
                <MIPersonCard
                  entity={movieInsights.leastPopularWriter}
                  movieCount={movieInsights.leastPopularWriterMovieCount}
                  type={CreditRole.WRITER}
                  valueType={MIValueType.Least}
                  isCooperative={this.props.parentType === EntityType.PERSON && this.props.activeRole === CreditRole.WRITER}
                />
              </CRow>

            </CCardBody>
          </CCard>
        </CCol>
        <CCol   className="entityCol">
          <CRow>
            <CCol>
              <CCard>
                <CCardHeader>
                  <h2 className="text-center">Production Companies</h2>
                </CCardHeader>
                <CCardBody>
                  <h4 className="text-center text-muted">Popularity</h4>
                  <hr/>
                  <CRow className="justify-content-center ">
                    <MICompanyCard
                      company={movieInsights.mostPopularProductionCompany}
                      movieCount={movieInsights.mostPopularProductionCompanyMovieCount}
                      valueType={MIValueType.Most}
                      coProducing={this.props.parentType === EntityType.COMPANY}
                    />
                    <MIDivider spacing={10}/>
                    <MICompanyCard
                      company={movieInsights.leastPopularProductionCompany}
                      movieCount={movieInsights.leastPopularProductionCompanyMovieCount}
                      valueType={MIValueType.Least}
                      coProducing={this.props.parentType === EntityType.COMPANY}
                    />
                  </CRow>
                </CCardBody>
              </CCard>
            </CCol>
          </CRow>
          <CRow>
            <CCol>
              <CCard>
                <CCardHeader>
                  <h2 className="text-center">Production Countries</h2>
                </CCardHeader>
                <CCardBody>
                  <h4 className="text-center text-muted">Popularity</h4>
                  <hr/>
                  <CRow className="justify-content-center ">
                    <MICountryCard
                      country={movieInsights.mostPopularProductionCountry}
                      valueType={MIValueType.Most}
                      movieCount={movieInsights.mostPopularProductionCountryMovieCount}
                      coProducing={this.props.parentType === EntityType.COUNTRY}
                    />
                    <MIDivider spacing={10}/>
                    <MICountryCard
                      country={movieInsights.leastPopularProductionCountry}
                      valueType={MIValueType.Least}
                      movieCount={movieInsights.leastPopularProductionCountryMovieCount}
                      coProducing={this.props.parentType === EntityType.COUNTRY}
                    />
                  </CRow>
                </CCardBody>
              </CCard>
            </CCol>
          </CRow>
          <CRow>
            <CCol>
              <CCard>
                <CCardHeader>
                  <h2 className="text-center">Genres</h2>
                </CCardHeader>
                <CCardBody>
                  <h4 className="text-center text-muted">Popularity</h4>
                  <hr/>
                  <CRow className="justify-content-center ">
                    <MIGenreCard
                      genre={movieInsights.mostPopularGenre}
                      movieCount={movieInsights.mostPopularGenreMovieCount}
                      valueType={MIValueType.Most}
                      coProducing={this.props.parentType === EntityType.GENRE}
                    />
                    <MIDivider spacing={10}/>
                    <MIGenreCard
                      genre={movieInsights.leastPopularGenre}
                      movieCount={movieInsights.leastPopularGenreMovieCount}
                      valueType={MIValueType.Least}
                      coProducing={this.props.parentType === EntityType.GENRE}
                    />
                  </CRow>
                </CCardBody>
              </CCard>
            </CCol>
          </CRow>
        </CCol>
      </CRow>
    )
  }
}

export default MIInsightsPanel;
