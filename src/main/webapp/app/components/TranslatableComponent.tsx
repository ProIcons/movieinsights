import {Component} from "react";
import {translate} from "app/translate";
import {TRANSLATION_ROOT_INDEX} from "app/config/constants";


export default abstract class TranslatableComponent<TProps, TState> extends Component<TProps, TState> {
  declare translationIndex: string;

  protected constructor(props, translationIndex: string) {
    super(props);
    this.translationIndex = translationIndex;
  }

  public readonly getTranslation = (index: string, params?: any): string => {
    return translate(`${TRANSLATION_ROOT_INDEX}.${this.translationIndex}.${index}`, params);
  }
  public readonly getPublicTranslation = (index: string, params?: any): string => {
    return translate(`${TRANSLATION_ROOT_INDEX}.${index}`, params);
  }
}
