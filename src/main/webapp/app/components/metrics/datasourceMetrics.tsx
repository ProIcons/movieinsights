/**
 * Adapted from https://github.com/jhipster/react-jhipster
 * licenced under Apache License 2.0 Copyright 2013-2020 Deepu KS and the respective JHipster contributors
 * Modified by Nikolas Mavropoulos for MovieInsights Project
 */
import * as React from 'react';
import { TextFormat } from 'app/utils/text-format-utils';
import {Table} from "react-bootstrap";

export interface IDatasourceMetricsProps {
  datasourceMetrics: any;
  twoDigitAfterPointFormat: string;
}

export class DatasourceMetrics extends React.Component<IDatasourceMetricsProps> {
  filterNaN = input => (isNaN(input) ? 0 : input);

  render() {
    const { datasourceMetrics, twoDigitAfterPointFormat } = this.props;
    return (
      <div>
        <h3>DataSource statistics (time in millisecond)</h3>
        <Table>
          <thead>
            <tr>
              <th>
                <span>Connection Pool Usage </span>
                (active: {datasourceMetrics.active.value}, min: {datasourceMetrics.min.value}, max: {datasourceMetrics.max.value}, idle:{' '}
                {datasourceMetrics.idle.value})
              </th>
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
              <td>Acquire</td>
              <td className="text-right">{datasourceMetrics.acquire.count}</td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.acquire.mean} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.acquire['0.0']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.acquire['0.5']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.acquire['0.75']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.acquire['0.95']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.acquire['0.99']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.acquire.max} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
            </tr>
            <tr>
              <td>Creation</td>
              <td className="text-right">{datasourceMetrics.creation.count}</td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.creation.mean} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.creation['0.0']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.creation['0.5']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.creation['0.75']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.creation['0.95']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.creation['0.99']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.creation.max} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
            </tr>
            <tr>
              <td>Usage</td>
              <td className="text-right">{datasourceMetrics.usage.count}</td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.usage.mean} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.usage['0.0']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.usage['0.5']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.usage['0.75']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.usage['0.95']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.usage['0.99']} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
              <td className="text-right">
                <TextFormat value={datasourceMetrics.usage.max} type={'number'} format={twoDigitAfterPointFormat} />
              </td>
            </tr>
          </tbody>
        </Table>
      </div>
    );
  }
}
