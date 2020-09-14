import { RouteProps } from 'react-router-dom';
import { ComponentType } from 'react';

export interface IBaseRouteObject {
  type: IType;
  exact?: boolean;
}

export interface IRoute extends RouteProps, IBaseRouteObject {
  name: string;
  routeComponentType?: ComponentType<any>;
  hasAnyAuthorities?: Array<string>;
  subRoutes?: IRouteObject[];
}

export interface IRedirect extends IBaseRouteObject {
  from: string;
  to: string;
}

export enum IType {
  REDIRECT,
  ROUTE,
  ANYROUTE,
}
export interface IRouter {
  path: string;
  routerComponentType: ComponentType<any>;
  routes: IRouteObject[];
  hasAnyAuthorities?: Array<string>;
}
export type IRouteObject = IRoute | IRedirect;
