import * as React from 'react';
import {TranslatableComponent} from "app/components/util";
import {CDataTable} from "@coreui/react";
import {Translate} from "app/translate";
import {APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT} from "app/config/constants";
import {getScopedSlots} from "app/utils/data-table-utils";

export interface IEndpointsRequestsMetricsProps {
  endpointsRequestsMetrics: any;
  wholeNumberFormat: string;
}

export class EndpointsRequestsMetrics extends TranslatableComponent<IEndpointsRequestsMetricsProps, any> {
  constructor(props) {
    super(props, "metrics", true);
  }

  getData = () => {
    const {endpointsRequestsMetrics} = this.props;
    return Object
      .entries(endpointsRequestsMetrics)
      .sort((v1, v2) => v1[0].localeCompare(v2[0]))
      .reduce(
        (obj, item) => {
          Object
            .keys(item[1])
            .map(method =>
              ({
                url: item[0],
                method,
                ...item[1][method]
              }))
            .forEach(v => obj.push(v));
          return obj
        }, []
      );
  }

  getFields = () => ([
    {key: 'url', label: this.getTranslation("endpoints.url"), _style: {width: "40%"}},
    {key: 'method', label: this.getTranslation("endpoints.method")},
    {key: 'count', label: this.getTranslation("endpoints.count")},
    {key: 'mean', _format: APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT, label: this.getTranslation("endpoints.mean")}
  ]);

  render() {
    return (
      <div>
        <hr style={{borderTop: "1px solid #8c8b8b", paddingTop: "2px", borderBottom: "1px solid #8c8b8b"}}/>
        <Translate component={"h3"} contentKey={"metrics.endpoints.title"}>Endpoints requests (time in
          millisecond)</Translate>
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
