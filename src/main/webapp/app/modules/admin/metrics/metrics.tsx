import React from 'react';
import {connect} from 'react-redux';
import {
  CacheMetrics,
  DatasourceMetrics,
  EndpointsRequestsMetrics,
  GarbageCollectorMetrics,
  HttpRequestMetrics,
  JvmMemory,
  JvmThreads,
  SystemMetrics
} from 'app/components/metrics';
import {Translate} from 'app/translate';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {
  APP_TIMESTAMP_FORMAT,
  APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT,
  APP_WHOLE_NUMBER_FORMAT
} from 'app/config/constants';
import {
  subscribeToMetrics,
  systemHealth,
  systemMetrics,
  systemThreadDump,
  unsubscribeFromMetrics
} from '../administration.reducer';
import {IRootState} from 'app/shared/reducers';
import {CButton, CCard, CCardBody, CCardHeader, CCardTitle, CCol, CRow} from "@coreui/react";
import {TranslatableComponent} from "app/components/util";
import {SystemStatus} from "app/components/metrics/systemStatus";

export interface IMetricsPageProps extends StateProps, DispatchProps {
}

export class MetricsPage extends TranslatableComponent<IMetricsPageProps, any> {
  declare intervalID;
  constructor(props) {
    super(props, "metrics", true);
  }

  componentDidMount() {
    this.getMetrics();
    this.props.subscribeToMetrics();
  }


  componentWillUnmount() {
    this.props.unsubscribeFromMetrics();
  }

  getMetrics = () => {
    if (!this.props.isFetching) {
      this.props.systemMetrics();
      this.props.systemThreadDump();
      this.props.systemHealth();
    }
  };

  render() {
    const {metrics, threadDump, isFetching} = this.props;
    return (
      <>
      <CCard>
        <CCardHeader>
          <CCardTitle>
            <Translate contentKey={"metrics.title"} component="h2">Control Center</Translate>
          </CCardTitle>
          <p>
            <CButton onClick={this.getMetrics} color={isFetching ? 'btn btn-danger' : 'btn btn-primary'}
                     disabled={isFetching}>
              <FontAwesomeIcon icon="sync"/>
              &nbsp;
              <Translate component="span" contentKey="health.refresh.button">
                Refresh
              </Translate>
            </CButton>
          </p>
        </CCardHeader>
        <CCardBody>
          <SystemStatus jvm={metrics.jvm} health={this.props.health} error={this.props.error} systemMetrics={metrics.processMetrics}/>
        </CCardBody>
      </CCard>
      <CCard>
        <CCardBody>
          <CRow>
            <CCol sm="12">
              <Translate component="h3" contentKey={"metrics.jvm.title"}>JVM Metrics</Translate>
              <hr/>
              <CRow>
                <CCol md="5">
                  {metrics && metrics.jvm ?
                    <JvmMemory jvmMetrics={metrics.jvm} wholeNumberFormat={APP_WHOLE_NUMBER_FORMAT}/> : ''}
                </CCol>
                <CCol md="3">{threadDump ?
                  <JvmThreads jvmThreads={threadDump} wholeNumberFormat={APP_WHOLE_NUMBER_FORMAT}/> : ''}</CCol>
                <CCol md="4">
                  {metrics && metrics.processMetrics ? (
                    <SystemMetrics
                      systemMetrics={metrics.processMetrics}
                      wholeNumberFormat={APP_WHOLE_NUMBER_FORMAT}
                      timestampFormat={APP_TIMESTAMP_FORMAT}
                    />
                  ) : (
                    ''
                  )}
                </CCol>
              </CRow>
            </CCol>
          </CRow>
          <hr/>
          {metrics && metrics.garbageCollector ? (
            <GarbageCollectorMetrics garbageCollectorMetrics={metrics.garbageCollector}
                                     wholeNumberFormat={APP_WHOLE_NUMBER_FORMAT}/>
          ) : (
            ''
          )}
        </CCardBody>
      </CCard>
      <CCard>
        <CCardBody>
          {metrics && metrics['http.server.requests'] ? (
            <HttpRequestMetrics
              requestMetrics={metrics['http.server.requests']}
              twoDigitAfterPointFormat={APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT}
              wholeNumberFormat={APP_WHOLE_NUMBER_FORMAT}
            />
          ) : (
            ''
          )}
          {metrics && metrics.services ? (
            <EndpointsRequestsMetrics endpointsRequestsMetrics={metrics.services}
                                      wholeNumberFormat={APP_WHOLE_NUMBER_FORMAT}/>
          ) : (
            ''
          )}
        </CCardBody>
      </CCard>
      <CCard>
        <CCardBody>
          {metrics.cache ? (
            <CRow>
              <CCol sm="12">
                <CacheMetrics cacheMetrics={metrics.cache}
                              twoDigitAfterPointFormat={APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT}/>
              </CCol>
            </CRow>
          ) : (
            ''
          )}
        </CCardBody>
      </CCard>
      <CCard>
        <CCardBody>
          {metrics.databases && JSON.stringify(metrics.databases) !== '{}' ? (
            <CRow>
              <CCol sm="12">
                <DatasourceMetrics datasourceMetrics={metrics.databases}
                                   twoDigitAfterPointFormat={APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT}/>
              </CCol>
            </CRow>
          ) : (
            ''
          )}
        </CCardBody>
      </CCard>
    </>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  error: storeState.administration.errorMessage != null,
  metrics: storeState.administration.metrics,
  health: storeState.administration.health,
  isFetching: storeState.administration.loading,
  threadDump: storeState.administration.threadDump,
});

const mapDispatchToProps = {systemMetrics, systemHealth, systemThreadDump,subscribeToMetrics,unsubscribeFromMetrics};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MetricsPage);
