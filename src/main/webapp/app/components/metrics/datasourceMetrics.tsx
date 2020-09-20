import * as React from 'react';
import {Translate} from "app/translate";
import {TranslatableComponent} from "app/components/util";
import _ from "lodash";
import {CDataTable} from "@coreui/react";
import {getScopedSlots} from "app/utils/data-table-utils";

export interface IDatasourceMetricsProps {
  datasourceMetrics: any;
  twoDigitAfterPointFormat: string;
}

export class DatasourceMetrics extends TranslatableComponent<IDatasourceMetricsProps, any> {
  constructor(props) {
    super(props, "metrics", true);
  }

  getData = () => {
    const {datasourceMetrics} = this.props;
    return Object
      .keys(datasourceMetrics)
      .sort((c1, c2) => c1.localeCompare(c2))
      .filter(k => ['acquire', 'creation', 'usage'].includes(k))
      .map(key => ({
        name: _.capitalize(key),
        count: datasourceMetrics[key]['count'],
        mean: datasourceMetrics[key]['mean'],
        max: datasourceMetrics[key]['max'],
        totalTime: datasourceMetrics[key]['totalTime'],
        '0.0': datasourceMetrics[key]['0.0'],
        '0.5': datasourceMetrics[key]['0.5'],
        '0.75': datasourceMetrics[key]['0.75'],
        '0.95': datasourceMetrics[key]['0.95'],
        '0.99': datasourceMetrics[key]['0.99'],
      }));
  }

  getFields = () => ([
    {
      key: 'name',
      label: `${this.getTranslation("datasource.usage")} (active: ${this.props.datasourceMetrics.active.value}, min: ${this.props.datasourceMetrics.min.value}, max: ${this.props.datasourceMetrics.max.value}, idle: ${this.props.datasourceMetrics.idle.value})`
    },
    {key: 'count', label: this.getTranslation("datasource.count")},
    {key: 'mean', _format: this.props.twoDigitAfterPointFormat, label: this.getTranslation("datasource.mean")},
    {key: '0.0', _format: this.props.twoDigitAfterPointFormat, label: this.getTranslation("datasource.min")},
    {key: '0.5', _format: this.props.twoDigitAfterPointFormat, label: this.getTranslation("datasource.p50")},
    {key: '0.75', _format: this.props.twoDigitAfterPointFormat, label: this.getTranslation("datasource.p75")},
    {key: '0.95', _format: this.props.twoDigitAfterPointFormat, label: this.getTranslation("datasource.p95")},
    {key: '0.99', _format: this.props.twoDigitAfterPointFormat, label: this.getTranslation("datasource.p99")},
    {key: 'max', _format: this.props.twoDigitAfterPointFormat, label: this.getTranslation("datasource.max")},
    {key: 'totalTime', _format: this.props.twoDigitAfterPointFormat, label: this.getTranslation("datasource.totalTime")}
  ]);

  render() {
    return (
      <div>
        <Translate component="h3" contentKey={"metrics.datasource.title"}>DataSource statistics (time in
          millisecond)</Translate>
        <hr/>
        <CDataTable
          items={this.getData()}
          fields={this.getFields()}
          scopedSlots={getScopedSlots(this.getFields())}
          striped
          hover
          dark
        />
      </div>
    );
  }
}
