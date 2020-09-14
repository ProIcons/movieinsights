import * as React from 'react';
import { TextFormat } from 'app/utils/text-format-utils';
import { CProgress, CCol, CRow } from '@coreui/react';

export interface ISystemMetricsProps {
  systemMetrics: any;
  wholeNumberFormat: string;
  timestampFormat: string;
}

export class SystemMetrics extends React.Component<ISystemMetricsProps> {
  static convertMillisecondsToDuration(ms) {
    const times = {
      year: 31557600000,
      month: 2629746000,
      day: 86400000,
      hour: 3600000,
      minute: 60000,
      second: 1000
    };
    let timeString = '';
    let plural = '';
    for (const key in times) {
      if (Math.floor(ms / times[key]) > 0) {
        plural = Math.floor(ms / times[key]) > 1 ? 's' : '';
        timeString += Math.floor(ms / times[key]).toString() + ' ' + key.toString() + plural + ' ';
        ms = ms - times[key] * Math.floor(ms / times[key]);
      }
    }
    return timeString;
  }

  render() {
    const { systemMetrics, wholeNumberFormat, timestampFormat } = this.props;
    return (
      <div>
        <h4>System</h4>
        <CRow>
          <CCol md="4">Uptime</CCol>
          <CCol md="8" className="text-right">
            {SystemMetrics.convertMillisecondsToDuration(systemMetrics['process.uptime'])}
          </CCol>
        </CRow>
        <CRow>
          <CCol md="4">Start time</CCol>
          <CCol md="8" className="text-right">
            <TextFormat value={systemMetrics['process.start.time']} type="date" format={timestampFormat} />
          </CCol>
        </CRow>
        <CRow>
          <CCol md="9">Process CPU usage</CCol>
          <CCol md="3" className="text-right">
            <TextFormat value={100 * systemMetrics['process.cpu.usage']} type="number" format={wholeNumberFormat} /> %
          </CCol>
        </CRow>
        <CProgress animated value={100 * systemMetrics['process.cpu.usage']} color="success">
          <span>
            <TextFormat value={100 * systemMetrics['process.cpu.usage']} type="number" format={wholeNumberFormat} /> %
          </span>
        </CProgress>
        <CRow>
          <CCol md="9">System CPU usage</CCol>
          <CCol md="3" className="text-right">
            <TextFormat value={100 * systemMetrics['system.cpu.usage']} type="number" format={wholeNumberFormat} /> %
          </CCol>
        </CRow>
        <CProgress animated value={100 * systemMetrics['system.cpu.usage']} color="success">
          <span>
            <TextFormat value={100 * systemMetrics['system.cpu.usage']} type="number" format={wholeNumberFormat} /> %
          </span>
        </CProgress>
        <CRow>
          <CCol md="9">System CPU count</CCol>
          <CCol md="3" className="text-right">
            {systemMetrics['system.cpu.count']}
          </CCol>
        </CRow>
        <CRow>
          <CCol md="9">System 1m Load average</CCol>
          <CCol md="3" className="text-right">
            <TextFormat value={systemMetrics['system.load.average.1m']} type="number" format={wholeNumberFormat} />
          </CCol>
        </CRow>
        <CRow>
          <CCol md="7">Process files max</CCol>
          <CCol md="5" className="text-right">
            <TextFormat value={systemMetrics['process.files.max']} type="number" format={wholeNumberFormat} />
          </CCol>
        </CRow>
        <CRow>
          <CCol md="4">Process files open</CCol>
          <CCol md="8" className="text-right">
            <TextFormat value={systemMetrics['process.files.open']} type="number" format={wholeNumberFormat} />
          </CCol>
        </CRow>
      </div>
    );
  }
}
