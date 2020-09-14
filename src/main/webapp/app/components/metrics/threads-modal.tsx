import * as React from 'react';
import { CModal, CModalHeader, CModalBody, CModalFooter, CButton, CInput, CBadge, CRow } from '@coreui/react';

import ThreadItem from './thread-item';
import {Table} from "react-bootstrap";

export interface IThreadsModalProps {
  showModal: boolean;
  handleClose: ()=>void;
  threadDump: any;
}

export interface IThreadsModalState {
  badgeFilter: string;
  searchFilter: string;
}

export class ThreadsModal extends React.Component<IThreadsModalProps, IThreadsModalState> {
  state: IThreadsModalState = {
    badgeFilter: '',
    searchFilter: ''
  };

  computeFilteredList = () => {
    const { badgeFilter, searchFilter } = this.state;
    let filteredList = this.props.threadDump.threads;
    if (badgeFilter !== '') {
      filteredList = filteredList.filter(t => t.threadState === badgeFilter);
    }
    if (searchFilter !== '') {
      filteredList = filteredList.filter(t => t.lockName && t.lockName.toLowerCase().includes(searchFilter.toLowerCase()));
    }
    return filteredList;
  };

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
    return { threadDumpAll, threadDumpRunnable, threadDumpWaiting, threadDumpTimedWaiting, threadDumpBlocked };
  };

  getBadgeClass = threadState => {
    if (threadState === 'RUNNABLE') {
      return 'badge-success';
    } else if (threadState === 'WAITING') {
      return 'badge-info';
    } else if (threadState === 'TIMED_WAITING') {
      return 'badge-warning';
    } else if (threadState === 'BLOCKED') {
      return 'badge-danger';
    }
  };

  updateBadgeFilter = badge => () => this.setState({ badgeFilter: badge });

  updateSearchFilter = event => this.setState({ searchFilter: event.target.value });

  render() {
    const { showModal, handleClose, threadDump } = this.props;
    let counters = {} as any;
    let filteredList = null;
    if (threadDump && threadDump.threads) {
      counters = this.computeCounters();
      filteredList = this.computeFilteredList();
    }

    return (
      <CModal show={showModal} onClosed={handleClose} className="modal-lg">
        <CModalHeader closeButton={true}>Threads dump</CModalHeader>
        <CModalBody>
          <CBadge color="primary" className="hand" onClick={this.updateBadgeFilter('')}>
            All&nbsp;
            <CBadge shape="pill">{counters.threadDumpAll || 0}</CBadge>
          </CBadge>
          &nbsp;
          <CBadge color="success" className="hand" onClick={this.updateBadgeFilter('RUNNABLE')}>
            Runnable&nbsp;
            <CBadge shape="pill">{counters.threadDumpRunnable || 0}</CBadge>
          </CBadge>
          &nbsp;
          <CBadge color="info" className="hand" onClick={this.updateBadgeFilter('WAITING')}>
            Waiting&nbsp;
            <CBadge shape="pill">{counters.threadDumpWaiting || 0}</CBadge>
          </CBadge>
          &nbsp;
          <CBadge color="warning" className="hand" onClick={this.updateBadgeFilter('TIMED_WAITING')}>
            Timed Waiting&nbsp;
            <CBadge shape="pill">{counters.threadDumpTimedWaiting || 0}</CBadge>
          </CBadge>
          &nbsp;
          <CBadge color="danger" className="hand" onClick={this.updateBadgeFilter('BLOCKED')}>
            Blocked&nbsp;
            <CBadge shape="pill">{counters.threadDumpBlocked || 0}</CBadge>
          </CBadge>
          &nbsp;
          <div className="mt-2">&nbsp;</div>
          <CInput type="text" className="form-control" placeholder="Filter by Lock Name..." onChange={this.updateSearchFilter} />
          <div style={{ padding: '10px' }}>
            {filteredList
              ? filteredList.map((threadDumpInfo, i) => (
                <div key={`dump-${i}`}>
                  <h6>
                    {' '}
                    <span className={'badge ' + this.getBadgeClass(threadDumpInfo.threadState)}>{threadDumpInfo.threadState}</span>
                    &nbsp;
                    {threadDumpInfo.threadName} (ID {threadDumpInfo.threadId}
                    )&nbsp;
                  </h6>
                  <ThreadItem threadDumpInfo={threadDumpInfo} />
                  <CRow>
                    <Table responsive>
                      <thead>
                      <tr>
                        <th>Blocked Time</th>
                        <th>Blocked Count</th>
                        <th>Waited Time</th>
                        <th>Waited Count</th>
                        <th>Lock Name</th>
                      </tr>
                      </thead>
                      <tbody>
                      <tr key={threadDumpInfo.lockName}>
                        <td>{threadDumpInfo.blockedTime}</td>
                        <td>{threadDumpInfo.blockedCount}</td>
                        <td>{threadDumpInfo.waitedTime}</td>
                        <td>{threadDumpInfo.waitedCount}</td>
                        <td className="thread-dump-modal-lock" title={threadDumpInfo.lockName}>
                          <code>{threadDumpInfo.lockName}</code>
                        </td>
                      </tr>
                      </tbody>
                    </Table>
                  </CRow>
                </div>
              ))
              : null}
          </div>
        </CModalBody>
        <CModalFooter>
          <CButton color="primary" onClick={handleClose}>
            Close
          </CButton>
        </CModalFooter>
      </CModal>
    );
  }
}

export default ThreadsModal;