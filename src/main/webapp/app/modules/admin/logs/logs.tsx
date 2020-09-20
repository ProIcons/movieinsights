import React, {Component, useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Translate} from 'app/translate';

import {changeLogLevel, getLoggers} from '../administration.reducer';
import {IRootState} from 'app/shared/reducers';
import {CButton, CButtonGroup, CCard, CCardBody, CCardHeader, CDataTable} from "@coreui/react";
import {TranslatableComponent} from "app/components/util";

export interface ILogsPageProps extends StateProps, DispatchProps {
}

export class Logs extends TranslatableComponent<ILogsPageProps, any> {
  constructor(props) {
    super(props,"logs",true);
  }
  componentDidMount() {
    this.props.getLoggers();
  }

  getData = () => {
    const logs = this.props.logs.loggers;
    return Object
      .keys(logs)
      .sort((c1, c2) => c1 === "ROOT" ? -1 : c2 === "ROOT" ? 1 : c1.localeCompare(c2))
      .map(key => ({
        name: key,
        level: logs[key]['effectiveLevel'],
      }));
  }
  changeLevel = (loggerName, level) => () => this.props.changeLogLevel(loggerName, level);
  getFields = () => ([
    {key: 'name', label: this.getTranslation("table.name")},
    {key: 'level', label: this.getTranslation("table.level"), filter: false, sorter: false}
  ]);
  getClassName = (level, check, className) => (level === check ? `btn btn-sm btn-${className}` : 'btn btn-sm btn-light');
  getScopedSlots = () => ({
    'level': (logger) => (
      <CButtonGroup>
        <CButton
          disabled={this.props.isFetching}
          onClick={this.changeLevel(logger.name, 'TRACE')}
          className={this.getClassName(logger.level, 'TRACE', 'primary')}
        >
          TRACE
        </CButton>
        <CButton
          disabled={this.props.isFetching}
          onClick={this.changeLevel(logger.name, 'DEBUG')}
          className={this.getClassName(logger.level, 'DEBUG', 'success')}
        >
          DEBUG
        </CButton>
        <CButton
          disabled={this.props.isFetching}
          onClick={this.changeLevel(logger.name, 'WARN')}
          className={this.getClassName(logger.level, 'WARN', 'warning')}
        >
          WARN
        </CButton>
        <CButton
          disabled={this.props.isFetching}
          onClick={this.changeLevel(logger.name, 'ERROR')}
          className={this.getClassName(logger.level, 'ERROR', 'danger')}
        >
          ERROR
        </CButton>
        <CButton
          disabled={this.props.isFetching}
          onClick={this.changeLevel(logger.name, 'OFF')}
          className={this.getClassName(logger.level, 'OFF', 'secondary')}
        >
          OFF
        </CButton>
      </CButtonGroup>
    )
  })

  render() {
    return (
      <CCard>
        <CCardHeader>
          <h2><Translate contentKey="logs.title">Logs</Translate></h2>
          <p>
            <Translate contentKey="logs.nbloggers" interpolate={{total: Object.keys(this.props.logs.loggers).length}}>
              <>There are {Object.keys(this.props.logs.loggers).length} loggers.</>
            </Translate>
          </p>
        </CCardHeader>
        <CCardBody>
          <CDataTable
            items={this.getData()}
            fields={this.getFields()}
            scopedSlots={this.getScopedSlots()}
            sorter
            striped
            pagination
            hover
            itemsPerPage={10}
            itemsPerPageSelect={{label:this.getTranslation("itemsPerPage")}}
            dark
            footer
            tableFilter={{placeholder:" ",label:this.getTranslation("filter")}}
            columnFilter
            cleaner
          />
        </CCardBody>
      </CCard>
    );
  }
}

const mapStateToProps = ({administration}: IRootState) => ({
  logs: administration.logs,
  isFetching: administration.loading,
});

const mapDispatchToProps = {getLoggers, changeLogLevel};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Logs);
