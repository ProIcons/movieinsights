/**
 * Adapted from https://github.com/jhipster/react-jhipster
 * licenced under Apache License 2.0 Copyright 2013-2020 Deepu KS and the respective JHipster contributors
 * Modified by Nikolas Mavropoulos for MovieInsights Project
 */
import './thread-modal.scss'
import * as React from 'react';
import {
  CBadge,
  CButton,
  CButtonGroup,
  CCol,
  CCollapse,
  CDataTable,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CRow
} from '@coreui/react';

import ThreadItem from './thread-item';
import {DataTableField, getScopedSlots} from "app/utils/data-table-utils";
import {TranslatableComponent} from "app/components/util";

export interface IThreadsModalProps {
  showModal: boolean;
  handleClose: () => void;
  threadDump: any;
}

export interface IThreadsModalState {
  badgeFilter: string;
  searchFilter: string;
  threadStateFilter: { "threadState": string }
  details: any[];
}

export class ThreadsModal extends TranslatableComponent<IThreadsModalProps, IThreadsModalState> {
  constructor(props) {
    super(props, "metrics", true);

    this.state = {
      badgeFilter: '',
      searchFilter: '',
      threadStateFilter: undefined,
      details: []
    };
    console.warn(this.getData());
  }

  getItemId(index) {
    return `${index.threadId}-${index.threadName}`;
  }

  toggleDetails = (index) => {
    const position = this.state.details.indexOf(this.getItemId(index))
    let newDetails = this.state.details.slice()
    if (position !== -1) {
      newDetails.splice(position, 1)
    } else {
      newDetails = [...this.state.details, this.getItemId(index)]
    }
    this.setState({details: newDetails});
  }
  toggleFilter = (state) => {
    if (state === this.state.threadStateFilter?.threadState) {
      this.setState({threadStateFilter: {threadState: ""}})
    } else {
      this.setState({threadStateFilter: {threadState: state}})
    }
  }

  onClick = (item) => {
    this.toggleDetails(item);
  }

  computeCounters = () => {
    let threadDumpAll = 0;
    let threadDumpRunnable = 0;
    let threadDumpWaiting = 0;
    let threadDumpTimedWaiting = 0;
    let threadDumpBlocked = 0;

    this.props.threadDump.threads.forEach(t => {
      switch (t.threadState) {
        case 'RUNNABLE':
          threadDumpRunnable++;
          break;
        case 'WAITING':
          threadDumpWaiting++;
          break;
        case 'TIMED_WAITING':
          threadDumpTimedWaiting++;
          break;
        case 'BLOCKED':
          threadDumpBlocked++;
          break;
        default:
          break;
      }
    });

    threadDumpAll = threadDumpRunnable + threadDumpWaiting + threadDumpTimedWaiting + threadDumpBlocked;
    return {threadDumpAll, threadDumpRunnable, threadDumpWaiting, threadDumpTimedWaiting, threadDumpBlocked};
  };

  getBadgeClass = threadState => {
    if (threadState === '"RUNNABLE"') {
      return 'badge-success';
    } else if (threadState === '"WAITING"') {
      return 'badge-info';
    } else if (threadState === '"TIMED_WAITING"') {
      return 'badge-warning';
    } else if (threadState === '"BLOCKED"') {
      return 'badge-danger';
    }
  };

  getData = () => {
    return this.props.threadDump?.threads?.map((v) => ({...v, threadState: `"${v.threadState}"`})) as any[];
  }

  getFields = (): DataTableField[] => ([
    {
      key: 'threadState',
      label: 'State',
      _customFormat: (item) => (
        <td style={{textAlign: "right", width: "min-content"}}>
          <CBadge
            onClick={(e) => {
              this.toggleFilter(item.threadState);
              e.preventDefault();
              e.stopPropagation();
              e.nativeEvent.stopImmediatePropagation();
            }}
            className={'c-button btn-' + this.getBadgeClass(item.threadState).replace("badge-", "")}
          >
            {item.threadState.replaceAll('"', '')}
          </CBadge>
        </td>
      ),
      _style: {width: "min-content"}
    },
    {
      key: 'threadId',
      label: 'ID',
      _style: {width: "5%"},
      _customFormat: (item) => (
        <td style={{textAlign: "center"}}>
          {item.threadId}
        </td>
      )
    },
    {
      key: 'threadName',
      label: 'Thread Name',
      _customFormat: (item) => (
        <td style={{fontWeight: "bold"}}>
          {item.threadName}
        </td>
      )
    },
    {
      key: 'blockedTime',
      label: 'Blocked Time',
      sorter: false,
      filter: false,
      _style: {textAlign: "center"},
      _customFormat: (item) => (
        <td style={{textAlign: "center", width: "5%"}}>
          {item.blockedTime}
        </td>
      )
    },
    {
      key: 'blockedCount',
      label: 'Blocked Count',
      sorter: false,
      filter: false,
      _style: {textAlign: "center", width: "5%"},
      _customFormat: (item) => (
        <td style={{textAlign: "center"}}>
          {item.blockedCount}
        </td>
      )
    },
    {
      key: 'waitedTime',
      label: 'Waited Time',
      sorter: false,
      filter: false,
      _style: {textAlign: "center", width: "5%"},
      _customFormat: (item) => (
        <td style={{textAlign: "center"}}>
          {item.waitedTime}
        </td>
      )
    },
    {
      key: 'waitedCount',
      label: 'Waited Count',
      sorter: false,
      filter: false,
      _style: {textAlign: "center"},
      _customFormat: (item) => (
        <td style={{textAlign: "center"}}>
          {item.waitedCount}
        </td>
      )
    },
    {
      key: 'lockName',
      label: 'Lock Name',
      _customFormat: (item) => (
        <td className="thread-dump-modal-lock" title={item.lockName}>
          <code>{item.lockName}</code>
        </td>
      )
    }

  ]);
  getScopedSlots = () => {
    return {
      ...getScopedSlots(this.getFields()),
      details: (item) => (
        <CCollapse show={this.state.details.includes(this.getItemId(item))}>
          <ThreadItem threadDumpInfo={item}/>
        </CCollapse>
      )
    }
  }

  updateBadgeFilter = badge => () => this.toggleFilter(badge);

  updateSearchFilter = event => this.setState({searchFilter: event.target.value});

  render() {
    const {showModal, handleClose, threadDump} = this.props;
    let counters = {} as any;
    if (threadDump && threadDump.threads) {
      counters = this.computeCounters();
    }

    return (
      <span className={"threads-modal"}>
        <CModal show={showModal} onClosed={handleClose}>
          <CModalHeader closeButton={true}><h2>Threads dump</h2></CModalHeader>
          <CModalBody>
            <CRow>
              <CCol style={{display:"flex"}} className="justify-content-center">
                <CButtonGroup>
                  <CButton color="primary" className="hand" onClick={this.updateBadgeFilter('')}>
                  All&nbsp;
                    <CBadge shape="pill">{counters.threadDumpAll || 0}</CBadge>
                </CButton>
                  <CButton color="success" className="hand" onClick={this.updateBadgeFilter('"RUNNABLE"')}>
                  Runnable&nbsp;
                    <CBadge shape="pill">{counters.threadDumpRunnable || 0}</CBadge>
                </CButton>
                  <CButton color="info" className="hand" onClick={this.updateBadgeFilter('"WAITING"')}>
                  Waiting&nbsp;
                    <CBadge shape="pill">{counters.threadDumpWaiting || 0}</CBadge>
                </CButton>
                  <CButton color="warning" className="hand" onClick={this.updateBadgeFilter('"TIMED_WAITING"')}>
                  Timed Waiting&nbsp;
                    <CBadge shape="pill">{counters.threadDumpTimedWaiting || 0}</CBadge>
                </CButton>
                  <CButton color="danger" className="hand" onClick={this.updateBadgeFilter('"BLOCKED"')}>
                  Blocked&nbsp;
                    <CBadge shape="pill">{counters.threadDumpBlocked || 0}</CBadge>
                </CButton>
                </CButtonGroup>
              </CCol>
            </CRow>
            <CDataTable
              items={this.getData()}
              fields={this.getFields()}
              scopedSlots={this.getScopedSlots()}
              sorter
              striped
              pagination
              clickableRows
              hover
              columnFilterValue={this.state.threadStateFilter}
              itemsPerPage={10}
              itemsPerPageSelect={{label: this.getPublicTranslation("logs.itemsPerPage")}}
              dark
              onRowClick={this.onClick}
              footer
              columnFilter
              cleaner
            />
          </CModalBody>
          <CModalFooter>
            <CButton color="primary" onClick={handleClose}>
              Close
            </CButton>
          </CModalFooter>
        </CModal>
      </span>
    );
  }


}

export default ThreadsModal;
