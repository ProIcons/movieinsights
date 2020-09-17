import React, {Component, Ref} from "react";
import {CCard, CCardBody, CCardHeader, CCol, CRow} from "@coreui/react";
import MISearchBar from "app/components/MISearchBar";
import MediaQuery from "react-responsive";
import {MapChart} from "app/components/charts";
import MISideInfoPane from "app/components/MISideInfoPane";
import MIInsightsPanel from "app/components/MIInsightsPanel";
import {ACEntity} from "app/models/AutoComplete.model";
import {ICountryData} from "app/models/ICountryData";
import {IMovieInsights} from "app/models/IMovieInsights.Model";
import {BaseMovieInsightsPerYearContainer} from "app/models/BaseMovieInsights.Model";
import {CreditRole, TmdbEntityType} from "app/models/enumerations";
import {MIPersonRolePickerProps} from "app/components/MIPersonRolePicker";
import {EntityType} from "app/models/enumerations/EntityType.enum";

export interface MIDashboardProps {
  options: MIDashboardOptions;
}

export interface MIDashboardOptions {
  onSearchBarSelected?: (result: ACEntity) => void;
  onMapSelected?: (result: ICountryData) => void;
  onMapUnselected?: (result: ICountryData) => void;
  onYearPickerSelected?: (year: number) => void;
  onYearPickerUnselected?: () => void;
  onCreditBarSelect?: (role: CreditRole) => void;
  mapReference: Ref<MapChart>;
  movieInsights: IMovieInsights;
  countryData: ICountryData[];
  selectedCountry: ICountryData;
  entity: BaseMovieInsightsPerYearContainer<any>;
  isPerYear: boolean;
  year: number;
  roles: CreditRole[];
  activeRole: CreditRole;
  entityType: EntityType;
}

export default class MIDashboard extends Component<MIDashboardProps> {
  render() {
    let creditSelector: MIPersonRolePickerProps = null
    if (this.props.options.activeRole) {
      creditSelector = {
        selected: this.props.options.activeRole,
        onSelect: this.props.options.onCreditBarSelect,
        roles: this.props.options.roles
      }
    }

    return (
      <>
        <CCard>
          <CCardHeader>
            <CRow className="justify-content-center">
              <MISearchBar onSelected={this.props.options.onSearchBarSelected}/>
            </CRow>
          </CCardHeader>
        </CCard>
        <CCard>
          <CCardBody>
            <CRow style={{height: "min-content"}}>
              <MediaQuery minDeviceWidth={"675px"}>
                <CCol className="mi-map-col">
                  <MapChart
                    ref={this.props.options.mapReference}
                    countryData={this.props.options.countryData}
                    countrySelected={this.props.options.onMapSelected}
                    countryUnselected={this.props.options.onMapUnselected}
                    defaultSelectedCountry={this.props.options.selectedCountry}
                  />
                </CCol>
                <div className="c-vr d-md-none d-lg-block">&nbsp;</div>
              </MediaQuery>
              <CCol className="text-center mi-side-info-col">
                <MISideInfoPane
                  yearSelected={this.props.options.onYearPickerSelected}
                  yearUnselected={this.props.options.onYearPickerUnselected}
                  movieInsights={this.props.options.movieInsights}
                  movieInsightsData={this.props.options.entity}
                  isShowingPerYear={this.props.options.isPerYear}
                  year={this.props.options.year}
                  creditSelector={creditSelector}
                />
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
        <hr/>
        <MIInsightsPanel parentType={this.props.options.entityType} activeRole={this.props.options.activeRole} movieInsights={this.props.options.movieInsights}/>
      </>
    )
  }
}
