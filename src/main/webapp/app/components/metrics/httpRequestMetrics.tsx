/**
 * Adapted from https://github.com/jhipster/react-jhipster
 * licenced under Apache License 2.0 Copyright 2013-2020 Deepu KS and the respective JHipster contributors
 * Modified by Nikolas Mavropoulos for MovieInsights Project
 */
import * as React from 'react';
import {TextFormat} from 'app/utils/text-format-utils';
import {CDataTable, CProgress, CProgressBar} from '@coreui/react';
import {Table} from "react-bootstrap";
import {TranslatableComponent} from "app/components/util";
import {MIProgressBar} from "app/components/MIProgressBar";
import {Translate} from "app/translate";
import {DataTableField, getScopedSlots} from "app/utils/data-table-utils";

export interface IHttpRequestMetricsProps {
  requestMetrics: any;
  wholeNumberFormat: string;
  twoDigitAfterPointFormat: string;
}

export class HttpRequestMetrics extends TranslatableComponent<IHttpRequestMetricsProps> {
  constructor(props) {
    super(props, "metrics.jvm.http", true);
  }

  getData = () => {
    const requestMetrics = this.props.requestMetrics.percode;
    return Object
      .keys(requestMetrics)
      .map(key => ({
        code: key,
        count: requestMetrics[key]['count'],
        mean: requestMetrics[key]['mean'],
        max: requestMetrics[key]['max'],
      }));
  }

  getFields = () : DataTableField[] => ([
    {key: 'code', label: `${this.getTranslation("table.code")}`},
    {
      key: 'count', _customFormat: (item) => (
        <td style={{display:"flex",flexDirection:"row"}}>
          <div style={{width:"100px",marginTop:"-3px"}}>{item.count}</div>
          <MIProgressBar showPercentage showValue min="0" max={this.props.requestMetrics.all.count} value={item.count} color="success" format={this.props.twoDigitAfterPointFormat}/>
        </td>
      ),
      label: this.getTranslation("table.count")
    },
    {key: 'mean', _format: this.props.twoDigitAfterPointFormat, label: this.getTranslation("table.mean")},
    {key: 'max', _format: this.props.twoDigitAfterPointFormat, label: this.getTranslation("table.max")}
  ]);

  render() {
    const {requestMetrics, wholeNumberFormat} = this.props;
    return (
      <div>
        <Translate contentKey={"metrics.jvm.http.title"} component={"h3"}>HTTP requests (time in milliseconds)</Translate>
        <p>
          <Translate contentKey={"metrics.jvm.http.total"} component="span">Total requests:</Translate>{' '}
          <b>
            <TextFormat value={requestMetrics.all.count} type="number" format={wholeNumberFormat}/>
          </b>
        </p>
        <hr/>
        <CDataTable
          items={this.getData()}
          fields={this.getFields()}
          scopedSlots={getScopedSlots(this.getFields())}
          sorter
          striped
          pagination
          hover
          itemsPerPage={5}
          itemsPerPageSelect={{label: this.getPublicTranslation("logs.itemsPerPage")}}
          dark
          footer
          tableFilter={{placeholder: " ", label: this.getPublicTranslation("logs.filter")}}
          columnFilter
          cleaner
        />
      </div>
    );
  }
}
