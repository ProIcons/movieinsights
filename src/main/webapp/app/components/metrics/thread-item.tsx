/**
 * Adapted from https://github.com/jhipster/react-jhipster
 * licenced under Apache License 2.0 Copyright 2013-2020 Deepu KS and the respective JHipster contributors
 * Modified by Nikolas Mavropoulos for MovieInsights Project
 */
import * as React from 'react';
import {CCardHeader, CCol, CContainer, CRow} from '@coreui/react';

export interface IThreadItemProps {
    threadDumpInfo: any;
}

export interface IThreadItemState {
    collapse: boolean;
}

export class ThreadItem extends React.Component<IThreadItemProps, IThreadItemState> {
    state: IThreadItemState = {
        collapse: false
    };

    // toggleStackTrace = (e) => {
    //   this.setState({
    //     collapse: !this.state.collapse
    //   });
    //   e.preventDefault();
    // };

    render() {
        const {threadDumpInfo} = this.props;

        return (
            <CContainer>
                <CCardHeader>
                    <CRow className="break">
                        <CCol>
                            {Object.entries(threadDumpInfo.stackTrace).map(([stK, stV]: [string, any]) => (
                                <>
                                    <samp key={`detail-${stK}`}>
                                        {stV.className}.{stV.methodName}
                                        <code>
                                            ({stV.fileName}:<strong className={"text-success"}>{stV.lineNumber}</strong>)
                                        </code>

                                    </samp>
                                    <br/>
                                </>
                            ))}
                        </CCol>
                    </CRow>
                </CCardHeader>
            </CContainer>
        );
    }
}

export default ThreadItem;
