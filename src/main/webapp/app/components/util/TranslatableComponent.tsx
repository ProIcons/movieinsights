import {Component} from "react";
import {translate} from "app/translate";
import {TRANSLATION_ROOT_INDEX} from "app/config/constants";

export abstract class TranslatableComponent<TProps = any, TState = any> extends Component<TProps, TState> {
  declare translationIndex: string;
  declare isRoot: boolean;

  protected constructor(props, translationIndex: string, root?: boolean) {
    super(props);
    this.translationIndex = translationIndex;
    this.isRoot = root || false;
  }

  public readonly getTranslation = (index: string, params?: any): string => {
    return translate(`${!this.isRoot?`${TRANSLATION_ROOT_INDEX}.`:''}${this.translationIndex}.${index}`, params);
  }
  public readonly getAppTranslation = (index: string, params?: any): string => {
    return translate(`${TRANSLATION_ROOT_INDEX}.${index}`, params);
  }
  public readonly getPublicTranslation = (index: string, params?: any): string => {
    return translate(`${index}`, params);
  }
}
