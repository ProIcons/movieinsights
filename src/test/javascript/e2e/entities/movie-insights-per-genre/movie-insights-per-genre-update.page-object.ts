import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class MovieInsightsPerGenreUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.movieInsightsPerGenre.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  movieInsightsSelect: ElementFinder = element(by.css('select#movie-insights-per-genre-movieInsights'));
  genreSelect: ElementFinder = element(by.css('select#movie-insights-per-genre-genre'));

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

  async genreSelectLastOption() {
    await this.genreSelect.all(by.tagName('option')).last().click();
  }

  async genreSelectOption(option) {
    await this.genreSelect.sendKeys(option);
  }

  getGenreSelect() {
    return this.genreSelect;
  }

  async getGenreSelectedOption() {
    return this.genreSelect.element(by.css('option:checked')).getText();
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
    await this.genreSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
