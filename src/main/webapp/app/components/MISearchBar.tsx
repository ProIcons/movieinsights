import './MISearchBar.scss'
import React, {Component, ComponentProps} from "react";
import Autosuggest, {SuggestionSelectedEventData} from "react-autosuggest";
import {CInput, CInputGroup, CInputGroupText} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import AutosuggestHighlightMatch from 'autosuggest-highlight/match';
import AutosuggestHighlightParse from 'autosuggest-highlight/parse';
import {Service} from 'app/service'
import {TmdbUtils} from "app/utils/tmdb-utils";
import {TMDB_LOGO_SIZE, TMDB_PROFILE_SIZE} from "app/config/constants";
import {EntityType} from "app/models/enumerations";
import {ACEntity, ACResult, AutoComplete} from "app/models";
import {TranslatableComponent} from "app/components/util";
import _ from "lodash";


// https://developer.mozilla.org/en/docs/Web/JavaScript/Guide/Regular_Expressions#Using_Special_Characters
function escapeRegexCharacters(str) {
  return str.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

async function getSuggestions(value: string): Promise<ACResult[]> {
  const escapedValue = escapeRegexCharacters(value);
  if (escapedValue !== value) {
    return null;
  }
  try {
    const results: AutoComplete = (await Service.search(escapedValue)).data;
    return results._
      .map(result => {
        result.e.forEach(e => {
          e.i = result.i
        })
        return result;
      })
      .filter(result => result.e.length > 0);

  } catch (e) {
    return null;
  }
}



function getSuggestionImage(suggestion: ACEntity): JSX.Element {
  switch (suggestion.i) {
    case EntityType.COMPANY:
      if (suggestion.logoPath) {
        return (<img alt={""} src={TmdbUtils.getLogoUrl(TMDB_LOGO_SIZE.W92, suggestion.logoPath, false)}/>);
      } else {
        return (<CIcon name={'cid-building'}/>);
      }
    case EntityType.PERSON:
      if (suggestion.profilePath) {
        return (<img alt={""} className="radius" src={TmdbUtils.getProfileUrl(TMDB_PROFILE_SIZE.W45, suggestion.profilePath)}/>);
      } else {
        return (<FontAwesomeIcon className="text-info" style={{marginTop: "-3px"}} icon={"user"} size={"3x"}/>)
      }
    case EntityType.COUNTRY:
      const flagName = 'cif' + suggestion.iso31661[0].toUpperCase() + suggestion.iso31661.substring(1).toLowerCase();
      return (<CIcon name={flagName}/>)
    case EntityType.GENRE:
      return (<FontAwesomeIcon className="text-info" icon={"theater-masks"} size={"2x"}/>)
    default:
      return null;
  }
}

function getSectionSuggestions(section: ACResult): ACEntity[] {
  return section.e;
}

export interface MISearchBarProps {
  onSelected: (result: ACEntity) => void;
}

export interface MISearchBarState {
  value: string,
  suggestions: ACResult[],
  error: boolean
}

export default class MISearchBar extends TranslatableComponent<MISearchBarProps, MISearchBarState> {
  inputRef = React.createRef();

  constructor(props) {
    super(props,"searchBar");

    this.state = {
      value: '',
      suggestions: [],
      error: false
    };
  }

  onChange = (event, {newValue}) => {
    this.setState({
      value: newValue
    });
  };
  renderSectionTitle = (section: ACResult) => {
    return (
      <strong>{this.getTranslation(section.i.toLowerCase())}</strong>
    );
  }
  getSuggestionValue = (suggestion: ACEntity): string => {
    return suggestion.name;
  }

   renderSuggestion = (suggestion: ACEntity, {query}): JSX.Element => {
    let name : string;
     switch (suggestion.i) {
       case EntityType.COUNTRY:
         name= this.getAppTranslation(`productionCountry.translations.${suggestion.iso31661.toUpperCase()}`)
         break
       case EntityType.GENRE:
         name= this.getAppTranslation(`genre.translations.${_.camelCase(suggestion.name)}`)
         break;
       default:
         name = suggestion.name;
     }
    const matches = AutosuggestHighlightMatch(name, query);
    const parts = AutosuggestHighlightParse(name, matches);

    return (
      <div className={'suggestion-content'}>
        <div className="autosuggest__image">
          {getSuggestionImage(suggestion)}
        </div>
        <div className="name">
          {
            parts.map((part, index) => {
              const className = part.highlight ? 'highlight' : null;

              return (
                <span className={className} key={index}>{part.text}</span>
              );
            })
          }
        </div>
      </div>
    );
  }

  onSuggestionsFetchRequested = async ({value}) => {
    const suggestionsResult = await getSuggestions(value);
    const error = !suggestionsResult;
    this.setState({
      suggestions: suggestionsResult ?? [],
      error
    });
  };

  onSuggestionsClearRequested = () => {
    this.setState({
      suggestions: []
    });
  };
  onSuggestionSelected = (event, sug: SuggestionSelectedEventData<ACEntity>) => {
    if (this.props.onSelected)
      this.props.onSelected(sug.suggestion);
    this.setState({value: ''})
  }
  renderInputComponent = (props: ComponentProps<any>) => {
    const {errClass, ref, ...rest} = props;
    return (<CInputGroup className={`mi-search-input-group ${errClass}`}>
      <CInputGroupText
        style={{width: "80px"}}
        className="justify-content-center"
      >
        <CIcon
          name="cid-magnifying-glass"
          className="text-muted"
          size={"xl"}
        />
      </CInputGroupText>
      <CInput {...rest}/>
    </CInputGroup>)
  }

  onBlur = () => {
    this.setState({value: ''})
  }

  render() {
    const {value, suggestions} = this.state;
    const inputProps = {
      placeholder: this.getTranslation("placeholder"),
      value,
      onChange: this.onChange,
      onBlur: this.onBlur,
      errClass: this.state.suggestions.length > 0 || this.state.value === '' ? "" : (this.state.error ? "mi-search-fatal" : "mi-search-warning")
    };

    return (
      <Autosuggest<ACEntity, ACResult>
        multiSection={true}
        suggestions={suggestions}
        onSuggestionsFetchRequested={this.onSuggestionsFetchRequested}
        onSuggestionsClearRequested={this.onSuggestionsClearRequested}
        onSuggestionSelected={this.onSuggestionSelected}
        getSuggestionValue={this.getSuggestionValue}
        renderSuggestion={this.renderSuggestion}
        renderSectionTitle={this.renderSectionTitle}
        renderInputComponent={this.renderInputComponent}
        getSectionSuggestions={getSectionSuggestions}
        inputProps={inputProps}/>
    );
  }
}
