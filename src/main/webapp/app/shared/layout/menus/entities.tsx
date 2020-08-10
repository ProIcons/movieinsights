import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/movie">
      <Translate contentKey="global.menu.entities.movie" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/person">
      <Translate contentKey="global.menu.entities.person" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/credit">
      <Translate contentKey="global.menu.entities.credit" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/banned-entity">
      <Translate contentKey="global.menu.entities.bannedEntity" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/genre">
      <Translate contentKey="global.menu.entities.genre" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/vote">
      <Translate contentKey="global.menu.entities.vote" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/production-country">
      <Translate contentKey="global.menu.entities.productionCountry" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/production-company">
      <Translate contentKey="global.menu.entities.productionCompany" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/movie-insights">
      <Translate contentKey="global.menu.entities.movieInsights" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/movie-insights-per-country">
      <Translate contentKey="global.menu.entities.movieInsightsPerCountry" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/movie-insights-per-company">
      <Translate contentKey="global.menu.entities.movieInsightsPerCompany" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/movie-insights-per-person">
      <Translate contentKey="global.menu.entities.movieInsightsPerPerson" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/movie-insights-per-genre">
      <Translate contentKey="global.menu.entities.movieInsightsPerGenre" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/movie-insights-per-year">
      <Translate contentKey="global.menu.entities.movieInsightsPerYear" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
