import numeral from 'numeral';

(function () {
  numeral.register('locale', 'el', {
    delimiters: {
      thousands: '.',
      decimal: ',',
    },
    abbreviations: {
      thousand: 'χιλ',
      million: 'εκ',
      billion: 'δισ',
      trillion: 'τρισ',
    },
    ordinal(number) {
      // In Greek there 3 genders of nouns.
      // I just put here the masculin form for the ordinal
      return 'ος';
    },
    currency: {
      symbol: '€',
    },
  });
})();
