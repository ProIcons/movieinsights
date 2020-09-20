import './MIDivider.scss'
import React, {Component} from "react";

export interface MIDividerProps {
  spacing?: number;
}

export class MIDivider extends Component<MIDividerProps> {
  constructor(props) {
    super(props);
  }

  render() {
    const spacing = this.props.spacing ? this.props.spacing : 10;
    return (
      <div className="mi-divider c-vr"
           style={{marginLeft: `${spacing}px`, marginRight: `${spacing}px`}}/>
    );
  }
}

export default MIDivider
