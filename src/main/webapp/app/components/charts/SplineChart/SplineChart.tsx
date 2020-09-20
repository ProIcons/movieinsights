import './SplineChart.scss'
import React, {Component, HTMLProps} from 'react'
import HighchartsReact from "highcharts-react-official";
import Highcharts from 'highcharts/highcharts';
import {deepObjectsMerge} from '@coreui/utils/src'
import {deepEqual} from "app/utils";

export interface SplineChartProps extends HTMLProps<any> {
  series: any[];
  dataMapper?: (names: string[], data: any[]) => Highcharts.SeriesSplineOptions[];
  namesMapper?: string[];
  options?: Highcharts.Options;
}

export interface SplineChartState {
  options?: Highcharts.Options;
  containerProps?: HTMLProps<any>;
  series: any[];
}


class SplineChart extends Component<SplineChartProps, SplineChartState> {
  constructor(props) {
    super(props);
    const {series, dataMapper, options, namesMapper, ...restProps} = this.props;
    const _options = this.injectOptionsToDefaults(options);
    this.injectSeriesToOptions(_options, series);

    const _containerProps = restProps ? deepObjectsMerge(this.defaultContainerProps, restProps) : this.defaultContainerProps;
    this.state = {
      options: _options,
      series,
      containerProps: _containerProps
    }
  }

  private defaultContainerProps = (): HTMLProps<any> => {
    return {}
  };

  private defaultOptions = (): Highcharts.Options => {
    return {
      chart: {
        type: 'spline',
        styledMode: true,
        zoomType: 'x',
        resetZoomButton: {
          position:{
            x: 0,
            y: -30
          }
        }
      },
      title: {
        text: undefined,
      },
      tooltip: {
        shared: true,
        outside: true
      },
      plotOptions: {
        series: {
          keys: ['x', 'y']
        },
        spline: {
          dashStyle:"ShortDash",
          marker: {
            enabled: false
          }
        }
      },
      xAxis: {
        allowDecimals: false,
        showEmpty: false,
        visible: false,
      },
      yAxis: {
        visible: false,
      },
      legend: {
        enabled: false
      },
      exporting: {
        enabled: false
      },
      credits: {
        enabled: false
      },
      series: [{
        name: "test",
        type: "spline"
      }]
    };
  };


  injectOptionsToDefaults = (options: Highcharts.Options): Highcharts.Options => {
    return options ? deepObjectsMerge(this.defaultOptions(), options) : this.defaultOptions();
  }
  injectSeriesToOptions = (options: Highcharts.Options, series) => {
    if (this.props.dataMapper) {
      options.series = this.props.dataMapper(this.props.namesMapper, series);
    } else {
      options.series = series;
    }
  }


  componentDidUpdate = (prevProps: Readonly<SplineChartProps>, prevState: Readonly<SplineChartState>) => {
    const {series, options} = this.props;

    const _options = this.injectOptionsToDefaults(options);
    this.injectSeriesToOptions(_options, series);

    if (!deepEqual(_options, prevState.options) || !deepEqual(this.props.series, prevState.series)) {
      this.setState({
        options: _options,
        series
      })
    }
  }

  render() {
    return (
      <HighchartsReact
        containerProps={this.state.containerProps}
        constructorType={'chart'}
        highcharts={Highcharts}
        options={this.state.options}
      />)
  }
}

export default SplineChart;
