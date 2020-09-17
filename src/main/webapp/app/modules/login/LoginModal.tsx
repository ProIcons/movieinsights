import React, {createRef} from 'react';
import {Translate, translate} from 'app/translate';
import {Link} from 'react-router-dom';
import {CAlert, CButton, CCol, CModal, CModalBody, CModalFooter, CModalHeader, CRow} from "@coreui/react";
import {Form, InputGroup} from "react-bootstrap";

export interface ILoginModalProps {
  showModal: boolean;
  loginError: boolean;
  handleLogin: (user: string, pass: string, remember: boolean) => void;
  handleClose: React.MouseEventHandler<any>;
}

interface LoginModalState {
  validated?: boolean;
  username: string;
  password: string;
  rememberMe: boolean;
}

class LoginModal extends React.Component<ILoginModalProps, LoginModalState> {
  constructor(props) {
    super(props);

    this.state = {
      validated: false,
      username: '',
      password: '',
      rememberMe: false
    }
  }

  handleSubmit = (event) => {

    if (event.currentTarget.checkValidity()) {
      const {handleLogin} = this.props;
      handleLogin(this.state.username,this.state.password,this.state.rememberMe);
    }

    event.preventDefault();
    event.stopPropagation();
    this.setState({validated:true});
  };

  render() {
    const {loginError, handleClose} = this.props;

    return (
      <CModal show={this.props.showModal} id="login-page" onClosed={handleClose} autoFocus={false}>
        <Form noValidate validated={this.state.validated} onSubmit={this.handleSubmit}>
          <CModalHeader id="login-title" closeButton>
            <Translate contentKey="login.title">Sign in</Translate>
          </CModalHeader>
          <CModalBody>
            <CRow>
              <CCol md={12}>
                {loginError ? (
                  <CAlert color="danger">
                    <Translate contentKey="login.messages.error.authentication">
                      <strong>Failed to sign in!</strong> Please check your credentials and try again.
                    </Translate>
                  </CAlert>
                ) : null}
              </CCol>
              <CCol md="12">
                <Form.Group>
                  <Form.Label>{translate('global.form.username.label')}</Form.Label>
                  <Form.Control
                    type="text"
                    name="username"
                    placeholder={translate('global.form.username.placeholder')}
                    onChange={(e)=>this.setState({username:e.target.value})}
                    required
                    autoFocus
                  />
                  <Form.Control.Feedback type="invalid">
                    Please enter your username.
                  </Form.Control.Feedback>
                </Form.Group>
                <Form.Group>
                  <Form.Label>{translate('login.form.password.placeholder')}</Form.Label>
                  <Form.Control
                    type="password"
                    name="password"
                    onChange={(e)=>this.setState({password:e.target.value})}
                    placeholder={translate('login.form.password.placeholder')}
                    required
                    autoFocus
                  />
                  <Form.Control.Feedback type="invalid">
                    Please enter your password.
                  </Form.Control.Feedback>
                </Form.Group>

                  <Form.Check
                    type="switch"
                    id="_login_switch"
                    name="rememberMe"
                    onChange={(e)=>this.setState({rememberMe:e.target.checked})}
                    label={translate('login.form.rememberme')}
                  />

              </CCol>
            </CRow>
          </CModalBody>
          <CModalFooter>
            <CButton color="secondary" onClick={handleClose} tabIndex={1}>
              <Translate contentKey="entity.action.cancel">Cancel</Translate>
            </CButton>{' '}
            <CButton color="primary" type="submit">
              <Translate contentKey="login.form.button">Sign in</Translate>
            </CButton>
          </CModalFooter>
        </Form>
      </CModal>
    );
  }
}

export default LoginModal;
