import './MIYearPicker.scss'
import React, {Component, HTMLProps} from "react";
import DateTime from 'react-datetime';
import {CButton, CInputGroup, CInputGroupAppend, CInputGroupPrepend, CInputGroupText} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import moment, {Moment} from "moment";

export interface MIYearPickerProps extends HTMLProps<any> {
  inputProps?: HTMLProps<any>
  yearValidation?: (currentYear: number) => boolean;
  minYear?: number,
  maxYear?: number,
  onYearSelect?: (year: number) => void;
  onYearUnselect?: () => void;
  value?: string;
}

export interface MIYearPickerState {
  value: string;
}

class MIYearPicker extends Component<MIYearPickerProps, MIYearPickerState> {

  declare onChange: (c) => void;

  constructor(props) {
    super(props);

    this.state = {
      value: this.props.value
    }
  }

  componentDidUpdate(prevProps: Readonly<MIYearPickerProps>, prevState: Readonly<any>, snapshot?: any) {
    if (this.props.value !== this.state.value && this.props.value !== prevProps.value) {
      this.setState({value: this.props.value});
    }
  }

  private renderInput = (props) => {
    const {onChange} = props;
    if (this.onChange !== onChange) {
      this.onChange = onChange;
    }
    return (
      <>
        <CInputGroup className="justify-content-md-center input-group-lg rtdMin">
          <CInputGroupPrepend>
            <CInputGroupText className="bg-info text-white">
              <CIcon name={'cil-calendar'}/>
            </CInputGroupText>
          </CInputGroupPrepend>
          <input className="form-control" {...props} value={this.state.value}/>
          <CInputGroupAppend>
            <CButton onClick={() => {
              this.onChange({target: {value: ''}});
            }} className={'bg-info text-white'}>
              <CIcon name={'cil-x'}/>
            </CButton>
          </CInputGroupAppend>
        </CInputGroup>

      </>
    )
  }

  private changed = (m: Moment) => {
    if (m && this.props.onYearSelect) this.props.onYearSelect(m.year());

    if (!m && this.props.onYearUnselect) this.props.onYearUnselect();

    this.setState({value: m ? m.year() + "" : ''});

  }

  private isValidDate = (current: moment.Moment): boolean => {
    if (!current)
      return false;
    if (this.props.yearValidation) {
      return this.props.yearValidation(current.year());
    }
    return (!this.props.minYear || current.year() >= this.props.minYear) && (!this.props.maxYear || current.year() <= this.props.maxYear)
  }

  render() {


    return (
      <DateTime
        onChange={this.changed as any}
        /* eslint-disable-next-line @typescript-eslint/ban-ts-comment */
        // @ts-ignore
        renderInput={this.renderInput as any}
        dateFormat={"Y"}
        disableYearsCache={true}
        timeFormat={false}
        closeOnSelect={true}
        isValidDate={this.isValidDate}
        {...this.props}
      />
    )
  }
}

export default MIYearPicker;
