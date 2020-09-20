import * as React from 'react';
import {TextFormat} from 'app/utils/text-format-utils';
import {CCard, CCardHeader, CDataTable} from '@coreui/react';
import {TranslatableComponent} from "app/components/util";
import {DataTableField, getScopedSlots} from "app/utils/data-table-utils";
import {MIProgressBar} from "app/components/MIProgressBar";
import {APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT} from "app/config/constants";
import {Translate} from "app/translate";

export interface IJvmMemoryProps {
  jvmMetrics: any;
  wholeNumberFormat: string;
}

export class JvmMemory extends TranslatableComponent<IJvmMemoryProps> {
  constructor(props) {
    super(props, "metrics.jvm", true);
  }

  getData = () => {
    const {jvmMetrics} = this.props;
    return Object
      .keys(jvmMetrics)
      .map(key => ({
        name: key,
        committed: jvmMetrics[key]['committed'] / 1048576,
        max: jvmMetrics[key]['max'] > -1 ? jvmMetrics[key]['max'] / 1048576 : undefined,
        used: jvmMetrics[key]['used'] / 1048576
      }));
  }

  getFields = (): DataTableField[] => ([
    {
      key: 'name',
      label: ``,
    },
    {key: 'committed', _format: this.props.wholeNumberFormat, _valueSuffix: "M", label: 'Committed'},
    {
      key: 'used', _customFormat: (item) => (
        <td style={{display: "flex", flexDirection: "row"}}>
          <div style={{width: "100px", marginTop: "-3px"}}><TextFormat value={item.used} type={"number"}
                                                                       format={APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT}/>M
          </div>
          <MIProgressBar showPercentage showValue min="0" max={item.max} value={item.used} color="success"
                         format={APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT}/>
        </td>
      ), _valueSuffix: "M", label: 'Used', _style: {width: "30%"}
    },
    {key: 'max', _format: this.props.wholeNumberFormat, _valueSuffix: "M", label: 'Max'},
  ]);

  render() {
    return (
      <CCard>
        <CCardHeader>
          <Translate component={"h3"} contentKey={"metrics.jvm.memory.title"}>Memory</Translate>
          <hr/>
          <CDataTable
            items={this.getData()}
            fields={this.getFields()}
            scopedSlots={getScopedSlots(this.getFields())}
            striped
            hover
            dark
          />
        </CCardHeader>
      </CCard>
    );
  }
}
