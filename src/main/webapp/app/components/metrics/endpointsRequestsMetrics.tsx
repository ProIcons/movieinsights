/**
 * Adapted from https://github.com/jhipster/react-jhipster
 * licenced under Apache License 2.0 Copyright 2013-2020 Deepu KS and the respective JHipster contributors
 * Modified by Nikolas Mavropoulos for MovieInsights Project
 */
import * as React from 'react';
import { TextFormat } from 'app/utils/text-format-utils';
import { Table } from 'react-bootstrap';

export interface IEndpointsRequestsMetricsProps {
  endpointsRequestsMetrics: any;
  wholeNumberFormat: string;
}

export class EndpointsRequestsMetrics extends React.Component<IEndpointsRequestsMetricsProps> {
  render() {
    const { endpointsRequestsMetrics, wholeNumberFormat } = this.props;
    return (
      <div>
        <h3>Endpoints requests (time in millisecond)</h3>
        <Table>
          <thead>
            <tr>
              <th>Method</th>
              <th>Endpoint url</th>
              <th>Count</th>
              <th>Mean</th>
            </tr>
          </thead>
          <tbody>
            {Object.entries(endpointsRequestsMetrics).map(([key, entry]) =>
              Object.entries(entry).map(([method, methodValue]) => (
                <tr key={key + '-' + method}>
                  <td>{method}</td>
                  <td>{key}</td>
                  <td>{methodValue.count}</td>
                  <td>
                    <TextFormat value={methodValue.mean} type="number" format={wholeNumberFormat} />
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </Table>
      </div>
    );
  }
}
