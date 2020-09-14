import './MIMovieInfoModal.scss'
import MIBaseLoadableCard, {
  MIBaseLoadableCardProps,
  MIBaseLoadableCardState
} from "app/components/cards/MIBaseLoadableCard";
import {IMovie, defaultValue as movieDefaultValue} from "app/models/IMovie.Model";
import React from "react";
import {CButton, CCardFooter, CCol, CModal, CModalBody, CModalFooter, CModalHeader, CRow} from "@coreui/react";
import numeral from "app/utils/numeral-utils";
import {MIValueNumeralFormat} from "app/components/cards/MIChartCard";
import moment from "moment";
import CIcon from "@coreui/icons-react";
import {TmdbUtils} from "app/utils/tmdb-utils";
import {MOVIE_POSTER_PLACEHOLDER, TMDB_BACKDROP_SIZE, TMDB_POSTER_SIZE} from "app/config/constants";

export interface MIMovieInfoModalState extends MIBaseLoadableCardState<IMovie> {
  open: boolean;
}
export interface MIMovieInfoModalProps extends MIBaseLoadableCardProps<IMovie> {
  open: boolean;
  onClose: () => void;
}

const getFieldValue = (val: number, format: MIValueNumeralFormat): string => {
  return numeral(val).format(format);
}
export default class MIMovieInfoModal extends MIBaseLoadableCard<MIMovieInfoModalProps, MIMovieInfoModalState, IMovie> {
  constructor(props) {
    super(props,movieDefaultValue);

    this.state = {
      ...this.state,
      open: this.props.open
    }
  }
  componentDidUpdate(prevProps: Readonly<MIBaseLoadableCardProps<IMovie>>, prevState: Readonly<MIBaseLoadableCardState<IMovie>>, snapshot?: any) {
    super.componentDidUpdate(prevProps,prevState,snapshot);
    if (this.state.open !== this.props.open) {
      this.setState({open: this.props.open});
    }
  }
  close = () => {
    this.setState({open:false})
    if (this.props.onClose) {
      this.props.onClose();
    }
  }
  protected subEntitiesNames(): string[] {
    return ["profile", "backdrop"];
  }
  getImdbLink() {
    return `https://www.imdb.com/title/${this.state.entity?.imdbId}/`;
  }

  getTmdbLink() {
    return `https://www.themoviedb.org/movie/${this.state.entity?.tmdbId}/`;
  }

  render() {

    return (
      <>
        <CModal
          show={this.state.open}
          size={"lg"}
          className="mi-movieInfo-card"
          closeOnBackdrop={true}
          centered={true}
          onClose={this.close}
        >
          <CModalHeader closeButton={true} className="mi-movieInfo-header text-center justify-content-md-center text-value-xl">
            <CCol style={{position:"absolute",marginTop:"-10px"}}>{this.state.entity?.name}</CCol>
          </CModalHeader>
          <CModalBody className="mi-movieInfo-card-container text-center justify-content-md-center">
            <div className="mi-movieInfo-card-backdrop"><img
              src={this.state.entity?.backdropPath?TmdbUtils.getBackdropUrl(TMDB_BACKDROP_SIZE.W780,this.state.entity.backdropPath):""}/></div>
            <div className="mi-movieInfo-card-poster"><img
              src={this.state.entity?.posterPath?TmdbUtils.getPosterUrl(TMDB_POSTER_SIZE.W342,this.state.entity.posterPath):MOVIE_POSTER_PLACEHOLDER}/></div>
            <div className="mi-movieInfo-card-content">
              <CRow style={{height: "100%", display: "flex"}} className="align-middle">
                <CCol className="align-self-center">
                  <div className="font-weight-bold font-2xl">Revenue</div>
                  <hr/>
                  {this.state.entity ? (
                    <div
                      className="text-value-md text-muted mi-money-field">{getFieldValue(this.state.entity?.revenue, MIValueNumeralFormat.Money)}</div>
                  ) : null}
                </CCol>
                <div className={"c-vr"} style={{marginRight:"-10px"}}/>
                <CCol>
                  <CRow>
                    <CCol>
                      <div className="font-weight-bold font-xl">Rating</div>
                      {this.state.entity ? (
                        <div className="text-value-md text-muted">
                          {getFieldValue(this.state.entity?.vote?.average, MIValueNumeralFormat.Decimal)}
                          <span
                            className="font-sm text-secondary"> ({getFieldValue(this.state.entity?.vote?.votes, MIValueNumeralFormat.Integer)})</span>
                        </div>
                      ) : null}
                    </CCol>
                  </CRow>
                  <hr style={{margin:"10px 1px 1px 1px"}}/>
                  <CRow>
                    <CCol>
                      <div className="font-weight-bold font-xl">Runtime</div>
                      {this.state.entity ? (
                        <div className="text-value-md text-muted">
                          {moment.utc().startOf("day").add(this.state?.entity.runtime,"minutes").format("hh:mm")}
                        </div>
                      ) : null}
                    </CCol>
                  </CRow>
                </CCol>
                <div className={"c-vr"} style={{marginLeft:"-10px"}}/>
                <CCol className="align-self-center">
                  <div className="font-weight-bold font-2xl">Budget</div>
                  <hr/>
                  {this.state.entity ? (
                    <div
                      className="text-value-md mi-money-field text-muted">{getFieldValue(this.state.entity?.budget, MIValueNumeralFormat.Money)}</div>
                  ) : null}
                </CCol>
              </CRow>
            </div>
          </CModalBody>
          <CCardFooter><div className="text-value-xl text-muted">Want to see more?</div></CCardFooter>
          <CModalFooter>
            <CButton style={{display:this.state.entity?.imdbId?"block":"none"}} color="warning" onClick={()=>window.location.href=this.getImdbLink()}><CIcon className={"btn-icon text-dark"} style={{margin:0}} size={"xl"} name={"cib-imdb"}/> <span className="text-dark">View on IMDb</span></CButton>{' '}
            <CButton style={{display:this.state.entity?.tmdbId?"block":"none"}} className="btn-tumblr text-value-md" onClick={()=>window.location.href=this.getTmdbLink()}><CIcon className={"btn-icon"} style={{margin:0}} size={"xl"}  name={"cib-tmdb"}/> View on TMDb</CButton>{' '}

          </CModalFooter>
        </CModal>
      </>
    )
  }

}
