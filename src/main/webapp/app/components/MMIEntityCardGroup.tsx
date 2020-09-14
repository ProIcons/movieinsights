import React, {Component, ReactElement} from 'react'
import MIPersonCard from "app/components/cards/MIPersonCard";
import MIMovieCard from "app/components/cards/MIMovieCard";
import MIGenreCard from "app/components/cards/MIGenreCard";
import MICountryCard from "app/components/cards/MICountryCard";
import MICompanyCard from "app/components/cards/MICompanyCard";
import {CCardGroup} from "@coreui/react";

export interface MMIEntityCardGroupProps {
  children: (ReactElement<MIPersonCard> | ReactElement<MIMovieCard> | ReactElement<MIGenreCard> | ReactElement<MICountryCard> | ReactElement<MICompanyCard>)[];
}

export default class MMIEntityCardGroup extends Component<MMIEntityCardGroupProps, any> {
  render() {
    return (
      <CCardGroup style={{maxWidth:`${(this.props.children.length*335)}px`}}>
      {
        this.props.children.map((card, index) => {
          if (index + 1 !== this.props.children.length) {
            return (<>
              {card}
              <div className="c-vr"/>
            </>);
          }
          return card;
        })
      }
      </CCardGroup>
    )
  }
}
