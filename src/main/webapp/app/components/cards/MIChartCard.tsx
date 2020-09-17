import './MIChartCard.scss'
import React, {Component, HTMLProps} from "react";
import SplineChart, {SplineChartProps} from "app/components/charts/SplineChart/SplineChart";
import {CCard, CCardBody, CCardFooter, CCardHeader, CCol, CRow} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import numeral from '../../utils/numeral-utils';
import {deepObjectsMerge} from '@coreui/utils/src'
import Skeleton, {SkeletonTheme} from "react-loading-skeleton";
import deepEqual from 'fast-deep-equal';
import {MIValueNumeralFormat} from "app/shared/enumerations/MIValueNumeralFormat";

export interface MIChartCardField {
  valueFormat?: MIValueNumeralFormat;
  valueFormatCustom?: string;
  subtitle: string;
  value: number;
  loaded: boolean;
}

export interface MIChartCardFooterField extends MIChartCardField {
  backgroundBasedOnValue?: boolean,
  defaultBackground?: string,
  negativeBackground?: string,
  positiveBackground?: string,
  zeroBackground?: string,
  enabledArrow?: boolean,
  arrowBackground?: string;
  arrowPositiveColor?: string;
  arrowPositive?: string;
  arrowNegativeColor?: string;
  arrowNegative?: string;
  arrowZeroColor?: string;
  arrowZero?: string;
}

export interface MIChartCardState {
  fields: MIChartCardField[];
  chartProps: SplineChartProps,
  footer?: MIChartCardFooterField;
  loading?: boolean;
}

export interface MIChartCardProps extends HTMLProps<any> {
  chartProps: SplineChartProps,
  headerIcon: string;
  headerBackgroundClassName: string;
  fields: MIChartCardField[];
  footer?: MIChartCardFooterField;
}

export const fieldDefaults = (): MIChartCardField => {
  return {
    valueFormat: MIValueNumeralFormat.Decimal,
    valueFormatCustom: '',
    subtitle: "",
    value: 0,
    loaded: false
  }
}
export const footerFieldDefaults = (): MIChartCardFooterField => {
  return {
    ...fieldDefaults(),
    enabledArrow: true,
    arrowBackground: 'bg-gradient-secondary',
    arrowPositive: 'cil-chevron-double-up',
    arrowPositiveColor: 'text-success',
    arrowNegative: 'cil-chevron-double-down',
    arrowNegativeColor: 'text-danger',
    arrowZero: 'cis-grip-lines',
    arrowZeroColor: 'text-dark',
    defaultBackground: '',
    backgroundBasedOnValue: true,
    negativeBackground: 'bg-gradient-danger',
    positiveBackground: 'bg-gradient-success',
    zeroBackground: 'bg-gradient-dark'

  }
}

const merge = (target, source) => {
  if (source) {
    return deepObjectsMerge(target, source);
  }
  return source;
}

class MIChartCard extends Component<MIChartCardProps, MIChartCardState> {
  constructor(props) {
    super(props);
    this.state = {
      footer: merge(footerFieldDefaults(), this.props.footer),
      fields: this.props.fields.map((field) => merge(fieldDefaults(), field)),
      chartProps: this.props.chartProps
    }
  }

  componentDidUpdate(prevProps: Readonly<MIChartCardProps>, prevState: Readonly<MIChartCardState>, snapshot?: any) {
    if (!deepEqual(prevProps,this.props)) {
      const state = {
        footer: merge(footerFieldDefaults(), this.props.footer),
        fields: this.props.fields.map((field) => merge(fieldDefaults(), field)),
        chartProps: this.props.chartProps
      };
      this.setState(state);
    }
  }

  componentDidMount() {
  }

  private getFooterConsts() {
    let background = '';
    const arrowBackground = this.state.footer.arrowBackground;
    let icon = '';
    let iconColor = '';

    if (this.state.footer.value > 0) {
      icon = this.state.footer.arrowPositive;
      iconColor = this.state.footer.arrowPositiveColor;
      background = this.state.footer.positiveBackground;
    } else if (this.state.footer.value < 0) {
      icon = this.state.footer.arrowNegative;
      iconColor = this.state.footer.arrowNegativeColor;
      background = this.state.footer.negativeBackground;
    } else {
      icon = this.state.footer.arrowZero;
      iconColor = this.state.footer.arrowZeroColor;
      background = this.state.footer.zeroBackground;
    }
    if (!this.state.footer.backgroundBasedOnValue) {
      background = this.state.footer.defaultBackground;
    }
    return {
      background,
      arrowBackground,
      icon,
      iconColor
    }
  }

  private getFieldValue = (field: MIChartCardField): string => {
    return numeral(field.value).format(field.valueFormat === MIValueNumeralFormat.Custom ? field.valueFormatCustom : field.valueFormat);
  }

  private getField = (field: MIChartCardField) => {
    return field.loaded ? (
      <>
        <div className="text-value-lg">{this.getFieldValue(field)}</div>
        <div className="text-muted small">{field.subtitle}</div>
      </>
    ) : (
      <>
        <div className="text-value-lg"><Skeleton/></div>
        <div className="text-muted small"><Skeleton/></div>
      </>
    );
  }

  private getIcon = (icon: string) => {
    return this.state.footer.loaded ? (
      <CIcon size="2xl" name={icon}/>
    ) : (
      <Skeleton style={{marginTop: "25px", marginLeft: "-7px"}} circle={true} height={25} width={25}/>
    )
  }

  private getBackground = (background: string) => {
    return !this.state.footer.loaded ? "bg-dark" : background;
  }


  renderHeader() {
    return (
      <CCardHeader className={`${this.props.headerBackgroundClassName} content-center text-white`}>
        <CIcon
          name={`${this.props.headerIcon}`}
          height="52"
          className="my-4"
        />
        <SplineChart
          className="position-absolute w-100 h-100"
          {...this.state.chartProps}
        />

      </CCardHeader>
    );
  }

  renderBody() {
    return (
      <SkeletonTheme color={"#646464"} highlightColor={"#d2d2d2"}>
        <CCardBody className="row text-center">
          {
            this.state.fields.map((field, ndx) => {
              return (
                <React.Fragment key={ndx}>
                  <div className="col">
                    {this.getField(field)}
                  </div>
                  {this.state.fields[ndx + 1] ? (<div className="c-vr"/>) : null}
                </React.Fragment>
              );
            })
          }
        </CCardBody>
      </SkeletonTheme>
    );
  }

  renderFooter() {

    if (this.state.footer) {
      const {
        background,
        arrowBackground,
        icon,
        iconColor
      } = this.getFooterConsts();
      return (
        <SkeletonTheme color={"#646464"} highlightColor={"#d2d2d2"}>
          <CCardFooter className={`text-white ${this.getBackground(background)}`}>
            <CRow>
              <CCol className={`${this.getBackground(background)}`}>
                {this.getField(this.state.footer)}
              </CCol>
              {this.state.footer.enabledArrow ? (
                <CCol className={`mi-chart-card-arrow ${this.getBackground(arrowBackground)} ${iconColor}`}>
                  {this.getIcon(icon)}
                </CCol>
              ) : null}
            </CRow>
          </CCardFooter>
        </SkeletonTheme>
      );
    }
    return null;
  }

  render() {
    return (
      <CCard className={`mi-entity-chart-card`}>
        {this.renderHeader()}
        {this.renderBody()}
        {this.renderFooter()}
      </CCard>
    );
  }
}

export default MIChartCard;
