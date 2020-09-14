import './MIDivider.scss'
import React, {Component} from "react";

export interface MIDividerProps {
  spacing?: number;
}

export default class MIDivider extends Component<MIDividerProps, MIDividerProps> {
  constructor(props) {
    super(props);

    this.state = {
      spacing: this.props.spacing ? this.props.spacing : 10
    }
  }

  componentDidUpdate(prevProps: Readonly<MIDividerProps>, prevState: Readonly<MIDividerProps>, snapshot?: any) {
    if (prevState.spacing !== this.props.spacing) {
      this.setState({spacing: this.props.spacing})
    }
  }

  render() {
    return (
      <div className="mi-divider c-vr"
           style={{marginLeft: `${this.state.spacing}px`, marginRight: `${this.state.spacing}px`}}/>
    );
  }
}
