import './MIEntityNotFound.scss'
import React, {Component} from "react";
import CIcon from "@coreui/icons-react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {TmdbEntityType} from "app/models/enumerations";

export interface MIEntityNotFoundProps {
  type: TmdbEntityType;
}

export class MIEntityNotFound extends Component<MIEntityNotFoundProps, any> {
  getIcon(): string {
    switch (this.props.type) {
      case TmdbEntityType.COUNTRY:
        return 'cil-flag';
      case TmdbEntityType.MOVIE:
        return 'cil-movie';
      case TmdbEntityType.PERSON:
        return 'cid-user';
      case TmdbEntityType.COMPANY:
        return 'cid-building'
      case TmdbEntityType.GENRE:
        return 'theater-masks';
      default:
        return null;
    }
  }

  getIconComponent(): JSX.Element {
    const icon : string = this.getIcon();
    if (icon.startsWith('ci')) {
      return <CIcon size="3xl" name={icon}/>
    } else if (icon) {
      return <FontAwesomeIcon icon={icon as any} size={"2x"}/>
    }
    return null;
  }

  render(): JSX.Element {
    return <>
      <div className="mi-entity-notfound">
        <div className="mi-entity-notfound-text text-warning">{this.getIconComponent()}<br/>No Data</div>
      </div>
    </>;
  }
}

export default MIEntityNotFound;
