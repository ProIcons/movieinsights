import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class MovieInsightsPerCompanyUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.movieInsightsPerCompany.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  movieInsightsSelect: ElementFinder = element(by.css('select#movie-insights-per-company-movieInsights'));
  companySelect: ElementFinder = element(by.css('select#movie-insights-per-company-company'));

  getPageTitle() {
    return this.pageTitle;
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

  async companySelectLastOption() {
    await this.companySelect.all(by.tagName('option')).last().click();
  }

  async companySelectOption(option) {
    await this.companySelect.sendKeys(option);
  }

  getCompanySelect() {
    return this.companySelect;
  }

  async getCompanySelectedOption() {
    return this.companySelect.element(by.css('option:checked')).getText();
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
    await this.movieInsightsSelectLastOption();
    await this.companySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
