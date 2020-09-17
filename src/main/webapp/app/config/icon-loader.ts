import React from 'react';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faSearch } from '@fortawesome/free-solid-svg-icons/faSearch';
import { faTheaterMasks } from '@fortawesome/free-solid-svg-icons/faTheaterMasks';
import { faGlobeAmericas } from '@fortawesome/free-solid-svg-icons/faGlobeAmericas';
import { faUser } from '@fortawesome/free-solid-svg-icons/faUser';

import { flagSet } from '@coreui/icons-pro/js/flag';
import { cibImdb } from '@coreui/icons-pro/js/brand/cib-imdb';
import { cidBuilding } from '@coreui/icons-pro/js/duotone/cid-building';
import { cidMagnifyingGlass } from '@coreui/icons-pro/js/duotone/cid-magnifying-glass';
import { cidMovieAlt } from '@coreui/icons-pro/js/duotone/cid-movie-alt';
import { cidUser } from '@coreui/icons-pro/js/duotone/cid-user';
import { cilCalendar } from '@coreui/icons-pro/js/linear/cil-calendar';
import { cilChevronDoubleDown } from '@coreui/icons-pro/js/linear/cil-chevron-double-down';
import { cilChevronDoubleUp } from '@coreui/icons-pro/js/linear/cil-chevron-double-up';
import { cilFlag } from '@coreui/icons-pro/js/linear/cil-flag';
import { cilGlobe } from '@coreui/icons-pro/js/linear/cil-globe';
import { cilGlobeAlt } from '@coreui/icons-pro/js/linear/cil-globe-alt';
import { cilMovie } from '@coreui/icons-pro/js/linear/cil-movie';
import { cilPen } from '@coreui/icons-pro/js/linear/cil-pen';
import { cilX } from '@coreui/icons-pro/js/linear/cil-x';
import { cisEuro } from '@coreui/icons-pro/js/solid/cis-euro';
import { cisGripLines } from '@coreui/icons-pro/js/solid/cis-grip-lines';

import { cifSu } from 'app/config/icons/cifSu';
import { cifIo } from 'app/config/icons/cifIo';
import { cibTmdb } from 'app/config/icons/cibTmdb';
import { cifGl } from 'app/config/icons/cifGl';
import { cifPr } from 'app/config/icons/cifPr';

const icons = Object.assign({}, flagSet, {
  cibImdb,
  cidBuilding,
  cilFlag,
  cidMovieAlt,
  cisEuro,
  cilPen,
  cilMovie,
  cilChevronDoubleDown,
  cilCalendar,
  cilX,
  cilChevronDoubleUp,
  cisGripLines,
  cidUser,
  cilGlobe,
  cilGlobeAlt,
  cibTmdb,
  cidMagnifyingGlass,
  cifGl,
  cifPr,
  cifSu,
  cifIo,
});

export const loadIcons = () => {
  library.add(faUser, faSearch, faTheaterMasks, faGlobeAmericas);
  (React as any).icons = icons;
};
