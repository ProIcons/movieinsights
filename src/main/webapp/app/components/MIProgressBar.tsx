import './MIProgressBar.scss'
import React, {Component} from "react";
import {CProgress, CProgressBar} from "@coreui/react";
import {TextFormat} from "app/utils";

export interface MIProgressBarProps extends CProgressBar {
  format?: string;
}

export class MIProgressBar extends Component<MIProgressBarProps> {
  constructor(props: MIProgressBarProps) {
    super(props);
  }

  render() {
    const {precision, showValue, showPercentage, format: _format, ...rest} = this.props;
    const format = _format ? _format : precision ? `0,0.[${'0'.repeat(precision)}]` : '';
    return (
      <CProgress className={"mi-progress-bar"}>
        <CProgressBar {...rest}/>
        {showValue ? (
          <div className={"mi-progress-bar-value"}>
            <TextFormat value={100 * rest.value / rest.max} type={"number"} format={format}/>{showPercentage ? '%' : ''}
          </div>) : null}
      </CProgress>
    )
  }
}
