import React, {useEffect, useState} from 'react';
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
import {
  Translate
} from 'app/translate';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {
  APP_TIMESTAMP_FORMAT,
  APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT,
  APP_WHOLE_NUMBER_FORMAT
} from 'app/config/constants';
import {systemMetrics, systemThreadDump} from '../administration.reducer';
import {IRootState} from 'app/shared/reducers';
import {CButton, CCard, CCardBody, CCardHeader, CCardTitle, CCol, CRow} from "@coreui/react";

export interface IMetricsPageProps extends StateProps, DispatchProps {
}

export const MetricsPage = (props: IMetricsPageProps) => {
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    props.systemMetrics();
    props.systemThreadDump();
  }, []);

  const getMetrics = () => {
    if (!props.isFetching) {
      props.systemMetrics();
      props.systemThreadDump();
    }
  };

  const {metrics, threadDump, isFetching} = props;

  return (
    <CCard>
      <CCardHeader>
        <CCardTitle>
          <h2 id="metrics-page-heading">Application Metrics</h2>
        </CCardTitle>
        <p>
          <CButton onClick={getMetrics} color={isFetching ? 'btn btn-danger' : 'btn btn-primary'}
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

        <CRow>
          <CCol sm="12">
            <h3>JVM Metrics</h3>
            <CRow>
              <CCol md="4">
                {metrics && metrics.jvm ?
                  <JvmMemory jvmMetrics={metrics.jvm} wholeNumberFormat={APP_WHOLE_NUMBER_FORMAT}/> : ''}
              </CCol>
              <CCol md="4">{threadDump ?
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

        {metrics && metrics.garbageCollector ? (
          <GarbageCollectorMetrics garbageCollectorMetrics={metrics.garbageCollector}
                                   wholeNumberFormat={APP_WHOLE_NUMBER_FORMAT}/>
        ) : (
          ''
        )}
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
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  metrics: storeState.administration.metrics,
  isFetching: storeState.administration.loading,
  threadDump: storeState.administration.threadDump,
});

const mapDispatchToProps = {systemMetrics, systemThreadDump};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MetricsPage);
