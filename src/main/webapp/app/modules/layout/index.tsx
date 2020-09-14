import React, {Component, ReactNode, ReactNodeArray} from "react";
import {IAppProps} from "app/App";
import Header from "app/modules/layout/header/Header";
import Footer from "app/modules/layout/footer/Footer";
import Content from "app/modules/layout/content/Content";


export type ILayoutProps = {
  appProps: IAppProps,
  children?: ReactNodeArray | ReactNode
}


export default class Layout extends Component<ILayoutProps, any> {
  constructor(props: ILayoutProps) {
    super(props);
  }


  render() {
    return (
      <div className="c-app c-default-layout c-dark-theme">
        <div className="c-wrapper">
          <Header appProps={this.props.appProps}/>
          <div className="c-body">
            <Content/>
          </div>
          <Footer/>
        </div>

      </div>
    );
  }
}

