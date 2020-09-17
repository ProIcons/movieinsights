import React, {Component} from "react";
import {ButtonGroup, ToggleButton} from "react-bootstrap";
import {deepEqual} from "app/utils";
import {CreditRole} from "app/models/enumerations";

export interface MIPersonRolePickerProps {
  roles: CreditRole[];
  onSelect?: (role: CreditRole) => void;
  selected: CreditRole;
}

export interface MIPersonRolePickerState {
  selected: CreditRole;
  options: { name: CreditRole, value: string, enabled: boolean }[]
}

export default class MIPersonRolePicker extends Component<MIPersonRolePickerProps, MIPersonRolePickerState> {


  constructor(props) {
    super(props);
    this.state = {
      selected: this.props.selected,
      options: this.getOptions()
    }
  }

  componentDidUpdate(prevProps: Readonly<MIPersonRolePickerProps>, prevState: Readonly<MIPersonRolePickerState>, snapshot?: any) {
    if (!deepEqual(this.state.options,this.getOptions())) {
      this.updateOptions();
    }
    if (this.props.selected !== prevProps.selected) {
      this.setState({selected: this.props.selected});
    }
  }

  getOptions = () => {
    return Object
      .keys(CreditRole)
      .map((k, v) => {
          return {
            name: CreditRole[k],
            value: k,
            enabled: this.props.roles.filter(z => z === CreditRole[k]).length > 0
          }
        }
      );
  }

  updateOptions = () => {
    this.setState({options:this.getOptions()});
  }


  _onChange = (e) => {
    const selected = CreditRole[e.currentTarget.value];
    this.setState({selected})
    if (this.props.onSelect) {
      this.props.onSelect(selected);
    }
  }

  render() {
    return (
      <>
        <ButtonGroup toggle>
          {this.state.options.map((radio, idx) => (
            <ToggleButton
              key={idx}
              type="radio"
              variant="secondary"
              name="radio"
              disabled={!radio.enabled}
              value={radio.value}
              checked={this.state.selected === CreditRole[radio.value]}
              onChange={this._onChange}
            >
              {radio.name}
            </ToggleButton>
          ))}
        </ButtonGroup>
      </>
    )
  }
}
