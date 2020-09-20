/**
 * Adapted from https://github.com/jhipster/react-jhipster
 * licenced under Apache License 2.0 Copyright 2013-2020 Deepu KS and the respective JHipster contributors
 * Rewrote by Nikolas Mavropoulos for MovieInsights Project using CoreUI
 */
import * as React from 'react';
import {TextFormat} from 'app/utils';
import {CDataTable} from "@coreui/react";
import {TranslatableComponent} from "app/components/util";
import {Translate} from "app/translate";
import {DataTableField, getScopedSlots} from "app/utils/data-table-utils";

export interface ICacheMetricsProps {
  cacheMetrics: any;
  twoDigitAfterPointFormat: string;
}

export class CacheMetrics extends TranslatableComponent<ICacheMetricsProps, any> {
  constructor(props) {
    super(props, "metrics", true);
  }

  filterNaN = input => (isNaN(input) ? 0 : input);

  getData = () => {
    const {cacheMetrics} = this.props;
    return Object
      .keys(cacheMetrics)
      .sort((c1, c2) => c1.localeCompare(c2))
      .map(key => ({
        name: key,
        hit: cacheMetrics[key]['cache.gets.hit'],
        miss: cacheMetrics[key]['cache.gets.miss'],
        gets: cacheMetrics[key]['cache.gets.hit'] + cacheMetrics[key]['cache.gets.miss'],
        hitPercent: this.filterNaN(
          100 *
          cacheMetrics[key]['cache.gets.hit'] /
          (cacheMetrics[key]['cache.gets.hit'] + cacheMetrics[key]['cache.gets.miss'])
        ),
        missPercent: this.filterNaN(
          100 *
          cacheMetrics[key]['cache.gets.miss'] /
          (cacheMetrics[key]['cache.gets.hit'] + cacheMetrics[key]['cache.gets.miss'])
        )
      }));
  }

  getFields = () : DataTableField[] => ([
    {key: 'name', label: this.getTranslation("cache.cachename"), _style: {width: "40%"}},
    {key: 'hit', label: this.getTranslation("cache.hits")},
    {key: 'miss', label: this.getTranslation("cache.misses")},
    {key: 'hitPercent', _format: this.props.twoDigitAfterPointFormat, label: this.getTranslation("cache.hitPercent")},
    {key: 'missPercent', _format: this.props.twoDigitAfterPointFormat, label: this.getTranslation("cache.missPercent")}
  ]);


  render() {
    return (
      <div>
        <Translate component="h3" contentKey={"metrics.cache.title"}>Ehcache statistics</Translate>
        <hr/>
        <CDataTable
          items={this.getData()}
          fields={this.getFields()}
          scopedSlots={getScopedSlots(this.getFields())}
          sorter
          striped
          pagination
          hover
          itemsPerPage={5}
          itemsPerPageSelect={{label:this.getPublicTranslation("logs.itemsPerPage")}}
          dark
          footer
          tableFilter={{placeholder:" ",label:this.getPublicTranslation("logs.filter")}}
          columnFilter
          cleaner
        />
      </div>
    );
  }
}
