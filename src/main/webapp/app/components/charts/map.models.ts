export class Data {
  public readonly _id;
  public readonly countryId;
  public readonly name;
  public readonly code3;
  public readonly value;
  public readonly year;

  constructor(point2) {
    const point = { ...point2 };
    this._id = point.id;
    this.countryId = point.countryId;
    this.name = point.name;
    this.code3 = point.iso31661 ? point.iso31661 : point.code3;
    this.value = point.movieCount ? point.movieCount : point.value;
  }

  public get id(): number {
    return this.code3;
  }
}
