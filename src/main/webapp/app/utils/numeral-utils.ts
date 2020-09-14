import _numeral from 'numeral';

const ordinalFn = (number: number): string => {
  return number === 1 ? 'er' : 'ème';
};
_numeral.register('locale', 'gr', {
  delimiters: {
    thousands: '.',
    decimal: ',',
  },
  abbreviations: {
    thousand: 'k',
    million: 'm',
    billion: 'b',
    trillion: 't',
  },
  ordinal: ordinalFn,
  currency: {
    symbol: '€',
  },
});
export const numeral = () => {
  // switch between locales
  _numeral.locale('gr');
  return _numeral;
};

export default numeral();
