import _numeral from 'numeral';
import 'app/utils/numeral/el.ts';

export const numeral = () => {
  // switch between locales
  _numeral.locale('el');
  return _numeral;
};

export default numeral();
