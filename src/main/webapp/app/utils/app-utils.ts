import { normalizeText } from 'app/utils/object-utils';
import { isMovie } from 'app/models/IMovie.Model';
import { isGenre } from 'app/models/IGenre.Model';
import { EntityType } from 'app/models/enumerations/EntityType.enum';
import { BaseNamedEntity } from 'app/models/BaseEntity.Model';
import { CreditRole } from 'app/models/enumerations';
import { isPerson } from 'app/models/IPerson.Model';
import { isCompany } from 'app/models/IProductionCompany.Model';
import { isCountry } from 'app/models/IProductionCountry.Model';

function _generateNavigationLink(entity?: BaseNamedEntity, role?: CreditRole, year?: number, entityType?: EntityType) {
  let type = undefined;
  if (entity) {
    if (entityType) {
      type = entityType.toLowerCase();
    } else if (isMovie(entity)) {
      type = 'movie';
    } else if (isPerson(entity)) {
      type = 'person';
    } else if (isCompany(entity)) {
      type = 'company';
    } else if (isCountry(entity)) {
      type = 'country';
    } else if (isGenre(entity)) {
      type = 'genre';
    }
  } else if (year) {
    type = 'general';
  }

  return `/app${type ? `/${type}` : ''}${entity ? `/${entity.id}-${normalizeText(entity.name)}` : ''}${
    role && type === 'person' ? `/${role}` : ''
  }${year ? `/${year}` : ''}`;
}

export namespace AppUtils {
  export const generateNavigationLink = _generateNavigationLink;
}
