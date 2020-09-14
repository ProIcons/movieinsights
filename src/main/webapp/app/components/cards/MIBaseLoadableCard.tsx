import React, {Component} from "react";

import {deepEqual} from "app/utils";
import {BaseEntity} from "app/models/BaseEntity.Model";

export interface MIBaseLoadableCardProps<T extends BaseEntity> {
  entity: T
}

export interface MIBaseLoadableCardState<T extends BaseEntity> extends MIBaseLoadableCardProps<T> {
  entityLoaded: boolean,
  loaded: boolean,
  subEntitiesLoaded: boolean
}

export default abstract class MIBaseLoadableCard<P extends MIBaseLoadableCardProps<T>, S extends MIBaseLoadableCardState<T>, T extends BaseEntity> extends Component<P, S> {
  private declare readonly entityDefaultValue: T;
  private declare entityStateMap: Map<string, boolean>;

  protected constructor(props, entityDefaultValue: T) {
    super(props);
    this.entityDefaultValue = entityDefaultValue;
    this.entityStateMap = new Map<string, boolean>();
    this.state = {
      entityLoaded: this.props.entity !== entityDefaultValue,
      loaded: false,
      entity: this.props.entity,
      subEntitiesLoaded: false,
    } as S

    [...this.subEntitiesNames()].forEach((v) => {
      this.entityStateMap.set(v, false);
    })
  }

  private areLoaded: () => boolean = () => {
    let loadedEntities = 0;
    const totalEntities = this.entityStateMap.size;
    this.entityStateMap.forEach((v, k) => {
      if (v)
        loadedEntities++;
    })

    return loadedEntities === totalEntities;
  }
  private reset = () => {
    if (this.entityStateMap.size > 0) {
      this.entityStateMap.forEach((v, k) => {
        this.entityStateMap.set(k, false);
      })
    }
  }

  protected abstract subEntitiesNames(): string[];

  protected readonly loaded = (name: string) => {
    if (this.entityStateMap.size > 0) {
      if (this.entityStateMap.has(name)) {
        this.entityStateMap.set(name, true);
        if (!this.state.loaded && this.state.entityLoaded && this.areLoaded()) {
          this.setState({subEntitiesLoaded: true, loaded: true});
        }
      } else {
        throw new Error("LoadedSubEntitiesState doesn't contain this key " + name);
      }
    }
  }

  public componentDidUpdate(prevProps: Readonly<MIBaseLoadableCardProps<T>>, prevState: Readonly<MIBaseLoadableCardState<T>>, snapshot?: any) {
    if (!deepEqual(this.state.entity,this.props.entity)) {
      if (this.props.entity !== this.entityDefaultValue) {
        if (this.state.entity !== this.entityDefaultValue) {
          this.reset();
          this.setState({
            entity: this.props.entity,
            entityLoaded: true,
            subEntitiesLoaded: false,
            loaded: false,
          });
        } else {
          this.setState({
            entity: this.props.entity,
            entityLoaded: true,
            subEntitiesLoaded: this.areLoaded(),
            loaded: this.areLoaded()
          })
        }
      } else {
        this.reset();
        this.setState({
          entity: this.props.entity,
          entityLoaded: false,
          subEntitiesLoaded: false,
          loaded: false,
        });
      }
    }
    if (!this.state.loaded && this.state.entityLoaded && this.areLoaded()) {
      this.setState({subEntitiesLoaded: true, loaded: true})
    }
  }
}
