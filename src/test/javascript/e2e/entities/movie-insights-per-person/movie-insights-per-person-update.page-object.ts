import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class MovieInsightsPerPersonUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.movieInsightsPerPerson.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  asSelect: ElementFinder = element(by.css('select#movie-insights-per-person-as'));
  movieInsightsSelect: ElementFinder = element(by.css('select#movie-insights-per-person-movieInsights'));
  personSelect: ElementFinder = element(by.css('select#movie-insights-per-person-person'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setAsSelect(as) {
    await this.asSelect.sendKeys(as);
  }

  async getAsSelect() {
    return this.asSelect.element(by.css('option:checked')).getText();
  }

  async asSelectLastOption() {
    await this.asSelect.all(by.tagName('option')).last().click();
  }
  async movieInsightsSelectLastOption() {
    await this.movieInsightsSelect.all(by.tagName('option')).last().click();
  }

  async movieInsightsSelectOption(option) {
    await this.movieInsightsSelect.sendKeys(option);
  }

  getMovieInsightsSelect() {
    return this.movieInsightsSelect;
  }

  async getMovieInsightsSelectedOption() {
    return this.movieInsightsSelect.element(by.css('option:checked')).getText();
  }

  async personSelectLastOption() {
    await this.personSelect.all(by.tagName('option')).last().click();
  }

  async personSelectOption(option) {
    await this.personSelect.sendKeys(option);
  }

  getPersonSelect() {
    return this.personSelect;
  }

  async getPersonSelectedOption() {
    return this.personSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.asSelectLastOption();
    await this.movieInsightsSelectLastOption();
    await this.personSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
