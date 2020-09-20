import React, {Component} from "react";
import {CBadge, CCol, CDropdown, CDropdownItem, CDropdownMenu, CDropdownToggle, CForm, CRow} from "@coreui/react";
import Select from 'react-select'
import CIcon from "@coreui/icons-react";
import {languages} from "app/config/translation";
import {IRootState} from "app/shared/reducers";
import {hasAnyAuthority} from "app/modules/admin/admin-route";
import {AUTHORITIES} from "app/config/constants";
import {setLocale} from "app/shared/reducers/locale";
import {getSession} from "app/shared/reducers/authentication";
import {getProfile} from "app/shared/reducers/application-profile";
import {connect} from "react-redux";
import {colors} from "app/shared/themes/react-select-theme";
import {Storage} from "app/utils";
import {IUser} from "app/models";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import Gravatar from "react-gravatar";

interface LocaleOption {
  value: string;
  icon: CIcon;
  label: string;
}

interface DropdownMenuState {
  localeOptions: LocaleOption[];
  selectedOption: LocaleOption;
}

interface DropdownMenuProps extends StateProps, DispatchProps {

}

export class DropdownMenu extends Component<DropdownMenuProps, DropdownMenuState> {
  constructor(props) {
    super(props);

    const opts = this.getLocaleOptions();
    this.state = {
      localeOptions: opts,
      selectedOption: opts.filter(l => l.value === this.props.currentLocale.toLowerCase())[0]
    };
  }

  componentDidUpdate(prevProps: Readonly<DropdownMenuProps>, prevState: Readonly<DropdownMenuState>, snapshot?: any) {
    if (this.state.selectedOption?.value !== this.props.currentLocale) {
      this.setState({
        selectedOption: this.state.localeOptions.filter(l => l.value === this.props.currentLocale.toLowerCase())[0]
      })
    }
  }

  getLocaleOptions(): LocaleOption[] {
    const localeOptions: LocaleOption[] = [];
    const languagesKeys: string[] = Object.keys(languages);
    const languagesValues: any[] = Object.values(languages);
    for (let index = 0; index < Object.values(languages).length; index++) {
      const localeOption: LocaleOption = {
        value: languagesKeys[index],
        icon: <CIcon size={"xl"} name={`cif-${languagesValues[index].iso31661.toLowerCase()}`}/>,
        label: languagesValues[index].name
      }
      localeOptions.push(localeOption);
    }
    return localeOptions;
  }

  getLabel(option: LocaleOption) {
    return (
      <div style={{alignItems: 'center', display: 'flex'}}>
        <div style={{marginRight: '0.5em', marginBottom: '-2px'}}>{option.icon}</div>
        <span style={{fontSize: 14}}>{option.label}</span>
      </div>
    )
  }

  renderAdminMenu() {
    if (!this.props.isAdmin)
      return null;

    return (
      <>
        <CDropdownItem
          header
          tag="div"
          className="text-center"
          color="light"
        >
          <strong>Administration</strong>
        </CDropdownItem>
        <CDropdownItem to={"/admin/metrics"}><CIcon name="cil-speedometer"
                                                    className="mr-2 text-warning"/> Metrics</CDropdownItem>
        <CDropdownItem to={"/admin/logs"}><CIcon name="cil-note" className="mr-2 text-primary"/> Logs</CDropdownItem>
      </>
    );
  }

  renderAccountMenu() {
    let accountMenu: JSX.Element = null;
    if (this.props.isAuthenticated) {
      accountMenu = (
        <>
          <CDropdownItem
            header
            tag="div"
            className="text-center border-bottom"
          >
            <Gravatar email={this.props.userAccount.email} style={{verticalAlign:"middle",display:"inline-block",borderRadius:"50%",border:"1px solid var(--secondary)"}} size={20}/> <span className="text-value">{this.props.userAccount.login}</span>
          </CDropdownItem>
          <CDropdownItem to={"/settings"}>
            <FontAwesomeIcon className="mr-2 text-warning c-icon" icon={"sliders-h"}/> Settings</CDropdownItem>
          <CDropdownItem to={"/logout"}>
            <FontAwesomeIcon className="mr-2 text-warning c-icon" icon={"sign-out-alt"}/> Logout</CDropdownItem>
        </>
      );
    } else {
      accountMenu = (
        <CDropdownItem to={"/login"}><FontAwesomeIcon className="mr-2 text-info c-icon" icon={"sign-in-alt"}/> Login</CDropdownItem>
      )
    }
    return (
      <>
        <CDropdownItem
          header
          tag="div"
          color="light"
          className="text-center"
        >
          <strong>Account</strong>
        </CDropdownItem>
        {accountMenu}
      </>
    )
  }

  private renderDevelopmentMenu() {
    if (this.props.isInProduction)
      return null;

    return (
      <>
        <CDropdownItem
          header
          tag="div"
          color="light"
          className="text-center"
        >
          <strong>Development</strong>
        </CDropdownItem>
        <CDropdownItem to={"/dev"}><CIcon name="cid-columns" className="mr-2 text-info"/> Dev Env</CDropdownItem>
      </>
    )
  }

  renderLocaleMenu() {
    return (
      <>
        <CDropdownItem
          header
          tag="div"
          color="light"
          className="text-center"
        >
          <strong>Locale</strong>
        </CDropdownItem>
        <CRow style={{padding:"5px"}}>
          <CCol>
            <CForm>
              <Select
                formatOptionLabel={this.getLabel}
                isSearchable={false}
                options={this.state.localeOptions}
                onChange={(option: LocaleOption) => {
                  if (option && !Array.isArray(option)) {
                    if (option.value !== this.state.selectedOption.value) {
                      Storage.session.set('locale', option.value);
                      this.props.setLocale(option.value);
                    }
                  }
                }}
                theme={theme => ({
                  ...theme,
                  colors
                })}
                value={this.state.selectedOption}
              />
            </CForm>
          </CCol>
        </CRow>

      </>
    );
  }

  render() {
    return (
      <CDropdown
        inNav
        className="c-header-nav-item mx-2"
      >
        <CDropdownToggle className="c-header-nav-link" caret={false}>
          <CIcon size={"xl"} name="cil-cog"/>
          {
            this.props.isAdmin ? (
              <CBadge shape="pill" color="danger">Adm</CBadge>
            ) : !this.props.isInProduction ? (
              <CBadge shape="pill" color="warning">Dev</CBadge>
            ) : null
          }
        </CDropdownToggle>
        <CDropdownMenu placement="bottom-end" className="pt-0">
          {this.renderAccountMenu()}
          {this.renderAdminMenu()}
          {this.renderDevelopmentMenu()}
          {this.renderLocaleMenu()}
        </CDropdownMenu>
      </CDropdown>
    );
  }


}

const mapStateToProps = ({authentication, applicationProfile, locale}: IRootState) => ({
  currentLocale: locale.currentLocale,
  isAuthenticated: authentication.isAuthenticated,
  userAccount: authentication.account as IUser,
  isAdmin: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.ADMIN]),
  isInProduction: applicationProfile.inProduction,
});

const mapDispatchToProps = {setLocale, getSession, getProfile};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DropdownMenu);
