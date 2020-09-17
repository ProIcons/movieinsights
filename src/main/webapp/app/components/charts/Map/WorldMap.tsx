import './WorldMap.scss';
import React, {Component, PropsWithRef, RefObject} from 'react'
import {renderToString} from 'react-dom/server'
import Highcharts, {
  Chart,
  Point,
  PointClickEventObject,
  PointInteractionEventObject,
  SeriesMapDataOptions,
  SeriesMapOptions
} from "highcharts";
import Highmaps from "highcharts/modules/map";
import HighchartsReact from "highcharts-react-official";
import worldMap from "./world.json";
import {ICountryData} from "app/models/ICountryData";
import MILoadingCircle from "app/components/MILoadingCircle";
import {HighchartsType} from "app/shared/interfaces/HighchartsType";

Highmaps(Highcharts);

export interface WorldMapProps extends PropsWithRef<any> {
  countryData: ICountryData[];
  countrySelected?: (data: ICountryData) => void;
  countryUnselected?: (data: ICountryData) => void;
}

interface WorldMapState {
  countryData: ICountryData[];
  chartObj: Chart;
  pointClicked: ICountryData,
  pointUnselected: boolean,
  pointSelected: ICountryData,
  pending: PendingState;
  mapOptions: Highcharts.Options;
  dataLoaded: boolean;
  dataDrawn: boolean
}

interface PendingState {
  point: number;
}

export class WorldMap extends Component<WorldMapProps, WorldMapState> {
  private readonly chartComponent: RefObject<HighchartsType>;

  constructor(props) {
    super(props);
    this.state = {
      countryData: this.props.countryData,
      chartObj: null,
      pointClicked: null,
      pointUnselected: false,
      pointSelected: null,
      pending: null,
      mapOptions: this.getMapOptions(),
      dataLoaded:false,
      dataDrawn:false
    };
    this.chartComponent = React.createRef<HighchartsType>();
  }


  componentDidUpdate(prevProps: Readonly<WorldMapProps>, prevState: Readonly<WorldMapState>, snapshot?: any) {
    if (this.state.countryData.length !== this.props.countryData.length) {
      this.setState({
        countryData: this.props.countryData,
        mapOptions: this.getMapOptions(),
        dataLoaded:true
      });

    }
  }


  public selectCountry(id: number) {
    if (this.state.chartObj && (this.state.chartObj.series[0] as any).valueData.filter(d => d !== null).length > 0) {
      const point: Point = this.state.chartObj.get(`${id}`) as Point;
      point.select(true);
    } else {
      this.setState({pending: {point: id}});
    }
  }

  public unselectCountry() {
    if (this.state.chartObj) {
      this.state.chartObj.getSelectedPoints().forEach(p => p.select(false));
      this.state.chartObj.zoomOut();
    } else {
      this.setState({pending: null})
    }
  }

  public getSelectedCountry() {
    return this.getCountryDataFromPoint(this.state.chartObj.getSelectedPoints()[0])
  }


  private pointClick = (point: SeriesMapDataOptions, e: PointClickEventObject) => {
    if (e.ctrlKey) {
      e.preventDefault();
    }
    if (point.id === this.state.pointSelected?.id) {
      this.setState({pointUnselected: true});
    }
  }

  private getCountryDataFromPoint = (point: SeriesMapDataOptions) => {
    if (point)
      return this.state.countryData.filter(data => data._id === +point.id)[0];
    return null;
  }

  private pointSelected = (point: SeriesMapDataOptions, e: PointInteractionEventObject) => {
    const countryData = this.getCountryDataFromPoint(point);
    this.setState({pointSelected: countryData});
    if (this.props.countrySelected) {
      this.props.countrySelected(countryData)
    }
    this.state.chartObj.zoomOut();
    const x = (point as any)._midX, y = (point as any)._midY;
    this.state.chartObj.mapZoom(0.5, x, y)
  }

  private pointUnselected = (point: SeriesMapDataOptions, e: PointInteractionEventObject) => {
    if (this.state.pointUnselected) {
      const countryData = this.getCountryDataFromPoint(point);
      this.setState({pointSelected: null, pointUnselected: false});
      if (this.props.countryUnselected) {
        this.props.countryUnselected(countryData);
      }
      this.state.chartObj.zoomOut();
    }
  }

  private getMapOptions() {
    const data = this.props.countryData;
    // eslint-disable-next-line @typescript-eslint/no-this-alias
    const _this = this;
    const mainMapOptions: Highcharts.Options = {
      chart: {
        animation: true,
        styledMode: true,
        events: {
          redraw() {
            if (_this.state.dataLoaded && !_this.state.dataDrawn) {
              _this.setState({dataDrawn:true});
            }
            else if (_this.chartComponent.current && _this.state.dataLoaded) {
              _this.chartComponent.current.chart.hideLoading();
            }
          }
        }
      },
      title: {
        text: ''
      },
      subtitle: {
        text: ''
      }, credits: {
        enabled: false
      },
      tooltip: {
        useHTML: true,
        formatter: function formt() {
          const name = (this.point as any).iso31661 === "MK" ? "North Macedonia" : this.point.name;
          let str = `<div class="text-info" style="text-align: center;font-weight: bold;font-size:24px;">${name}</div>`;
          str += `<div style="text-align: center;font-weight: bold;font-size:20px;"><span class="text-warning">Movies</span> ${this.point.value}</div>`
          return str;
        }
      },
      colorAxis: {
        min: 1,
        max: 100000,
        type: 'logarithmic',
        showInLegend: false
      },
      loading: {
        style: {opacity: 1}
      },
      mapNavigation: {
        enabled: true,
        enableButtons: false,
      },
      series: [
        {
          data: data as any,
          mapData: worldMap,
          joinBy: ['iso-a2', 'iso31661'] as any,
          keys: ['movieCount', 'value'],
          allowPointSelect: true,
          showInLegend: false,
          colorIndex: 5,
          cursor: 'pointer',
          dataLabels: {
            enabled: true,
            formatter: function format() {
              return (this.point as any).iso31661 === "MK" ? "North Macedonia" : this.point.name;
            },
            nullFormat: "",
          },
          type: 'map',
          borderWidth: 0.05,
          point: {
            events: {
              click(e) {
                _this.pointClick(this, e);
              },
              select(e) {
                _this.pointSelected(this, e)
              },
              unselect(e) {
                _this.pointUnselected(this, e)
              },
            }
          },
        } as SeriesMapOptions
      ]
    }

    return mainMapOptions;
  }

  private mapInitialized = (chart: Chart) => {
    this.setState({chartObj: chart});
    if (this.state.pending) {
      this.selectCountry(this.state.pending.point)
      this.setState({pending: null});
    }
    chart.showLoading(renderToString(<MILoadingCircle/>));
  }

  render() {
    return (
      <HighchartsReact
        ref={this.chartComponent}
        constructorType={"mapChart"}
        highcharts={Highcharts}
        options={this.state.mapOptions}
        callback={this.mapInitialized}
      />
    );
  }
}

export default WorldMap;
