import * as React from 'react';
import { TextFormat } from 'app/utils/text-format-utils';
import { CCol, CProgress, CRow } from '@coreui/react';
import {Table} from "react-bootstrap";

export interface IGarbageCollectorMetricsProps {
  garbageCollectorMetrics: any;
  wholeNumberFormat: string;
}

export class GarbageCollectorMetrics extends React.Component<IGarbageCollectorMetricsProps> {
  filterNaN = input => (isNaN(input) ? 0 : input);

  render() {
    const { garbageCollectorMetrics, wholeNumberFormat } = this.props;
    return (
      <div>
        <h3>Garbage Collection</h3>
        <CRow>
          <CCol md="4">
            <span>
              GC Live Data Size/GC Max Data Size (<TextFormat
                value={garbageCollectorMetrics['jvm.gc.live.data.size'] / 1048576}
                type={'number'}
                format={wholeNumberFormat}
              />M /{' '}
              <TextFormat value={garbageCollectorMetrics['jvm.gc.max.data.size'] / 1048576} type={'number'} format={wholeNumberFormat} />M)
            </span>
            <CProgress
              animated
              color="success"
              value={100 * garbageCollectorMetrics['jvm.gc.live.data.size'] / garbageCollectorMetrics['jvm.gc.max.data.size']}
            >
              <TextFormat
                value={100 * garbageCollectorMetrics['jvm.gc.live.data.size'] / garbageCollectorMetrics['jvm.gc.max.data.size']}
                type={'number'}
                format={wholeNumberFormat}
              />%
            </CProgress>
          </CCol>
          <CCol md="4">
            <span>
              GC Memory Promoted/GC Memory Allocated (<TextFormat
                value={garbageCollectorMetrics['jvm.gc.memory.promoted'] / 1048576}
                type={'number'}
                format={wholeNumberFormat}
              />M /{' '}
              <TextFormat value={garbageCollectorMetrics['jvm.gc.memory.allocated'] / 1048576} type={'number'} format={wholeNumberFormat} />M)
            </span>
            <CProgress
              animated
              color="success"
              value={100 * garbageCollectorMetrics['jvm.gc.memory.promoted'] / garbageCollectorMetrics['jvm.gc.memory.allocated']}
            >
              <TextFormat
                value={100 * garbageCollectorMetrics['jvm.gc.memory.promoted'] / garbageCollectorMetrics['jvm.gc.memory.allocated']}
                type={'number'}
                format={wholeNumberFormat}
              />%
            </CProgress>
          </CCol>
          <CCol md="4">
            <CRow>
              <CCol md="9">Classes loaded</CCol>
              <CCol md="3">{garbageCollectorMetrics.classesLoaded}</CCol>
            </CRow>
            <CRow>
              <CCol md="9">Classes unloaded</CCol>
              <CCol md="3">{garbageCollectorMetrics.classesUnloaded}</CCol>
            </CRow>
          </CCol>
        </CRow>
        <Table>
          <thead>
            <tr>
              <th />
              <th className="text-right">Count</th>
              <th className="text-right">Mean</th>
              <th className="text-right">Min</th>
              <th className="text-right">p50</th>
              <th className="text-right">p75</th>
              <th className="text-right">p95</th>
              <th className="text-right">p99</th>
              <th className="text-right">Max</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>jvm.gc.pause</td>
              <td className="text-right">
                <TextFormat value={garbageCollectorMetrics.count} type={'number'} format={'0,0.[000]'} />
              </td>
              <td className="text-right">
                <TextFormat value={garbageCollectorMetrics.mean} type={'number'} format={'0,0.[000]'} />
              </td>
              <td className="text-right">
                <TextFormat value={garbageCollectorMetrics['0.0']} type={'number'} format={'0,0.[000]'} />
              </td>
              <td className="text-right">
                <TextFormat value={garbageCollectorMetrics['0.5']} type={'number'} format={'0,0.[000]'} />
              </td>
              <td className="text-right">
                <TextFormat value={garbageCollectorMetrics['0.75']} type={'number'} format={'0,0.[000]'} />
              </td>
              <td className="text-right">
                <TextFormat value={garbageCollectorMetrics['0.95']} type={'number'} format={'0,0.[000]'} />
              </td>
              <td className="text-right">
                <TextFormat value={garbageCollectorMetrics['0.99']} type={'number'} format={'0,0.[000]'} />
              </td>
              <td className="text-right">
                <TextFormat value={garbageCollectorMetrics.max} type={'number'} format={'0,0.[000]'} />
              </td>
            </tr>
          </tbody>
        </Table>
      </div>
    );
  }
}
