import { Storage } from 'app/utils';

import { setLocale } from 'app/shared/reducers/locale';
import { TranslatorContext } from 'app/translate';
import { getUserLocale } from 'get-user-locale';

TranslatorContext.setDefaultLocale('en');
TranslatorContext.setRenderInnerTextForMissingKeys(false);

export const languages: any = {
  en: { name: 'English', iso31661: 'gb' },
  el: { name: 'Ελληνικά', iso31661: 'gr' },
  // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
};

export const locales = Object.keys(languages).sort();
const userLocale = getUserLocale().split('-')[0];
const actualLocale = locales.includes(userLocale) ? userLocale : 'en';
export const registerLocale = store => {
  store.dispatch(setLocale(Storage.session.get('locale', actualLocale)));
};
