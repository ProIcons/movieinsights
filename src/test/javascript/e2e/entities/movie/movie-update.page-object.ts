import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class MovieUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.movie.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  tmdbIdInput: ElementFinder = element(by.css('input#movie-tmdbId'));
  nameInput: ElementFinder = element(by.css('input#movie-name'));
  taglineInput: ElementFinder = element(by.css('input#movie-tagline'));
  descriptionInput: ElementFinder = element(by.css('textarea#movie-description'));
  revenueInput: ElementFinder = element(by.css('input#movie-revenue'));
  budgetInput: ElementFinder = element(by.css('input#movie-budget'));
  imdbIdInput: ElementFinder = element(by.css('input#movie-imdbId'));
  popularityInput: ElementFinder = element(by.css('input#movie-popularity'));
  runtimeInput: ElementFinder = element(by.css('input#movie-runtime'));
  posterPathInput: ElementFinder = element(by.css('input#movie-posterPath'));
  backdropPathInput: ElementFinder = element(by.css('input#movie-backdropPath'));
  releaseDateInput: ElementFinder = element(by.css('input#movie-releaseDate'));
  voteSelect: ElementFinder = element(by.css('select#movie-vote'));
  companiesSelect: ElementFinder = element(by.css('select#movie-companies'));
  countriesSelect: ElementFinder = element(by.css('select#movie-countries'));
  genresSelect: ElementFinder = element(by.css('select#movie-genres'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTmdbIdInput(tmdbId) {
    await this.tmdbIdInput.sendKeys(tmdbId);
  }

  async getTmdbIdInput() {
    return this.tmdbIdInput.getAttribute('value');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setTaglineInput(tagline) {
    await this.taglineInput.sendKeys(tagline);
  }

  async getTaglineInput() {
    return this.taglineInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
  }

  async setRevenueInput(revenue) {
    await this.revenueInput.sendKeys(revenue);
  }

  async getRevenueInput() {
    return this.revenueInput.getAttribute('value');
  }

  async setBudgetInput(budget) {
    await this.budgetInput.sendKeys(budget);
  }

  async getBudgetInput() {
    return this.budgetInput.getAttribute('value');
  }

  async setImdbIdInput(imdbId) {
    await this.imdbIdInput.sendKeys(imdbId);
  }

  async getImdbIdInput() {
    return this.imdbIdInput.getAttribute('value');
  }

  async setPopularityInput(popularity) {
    await this.popularityInput.sendKeys(popularity);
  }

  async getPopularityInput() {
    return this.popularityInput.getAttribute('value');
  }

  async setRuntimeInput(runtime) {
    await this.runtimeInput.sendKeys(runtime);
  }

  async getRuntimeInput() {
    return this.runtimeInput.getAttribute('value');
  }

  async setPosterPathInput(posterPath) {
    await this.posterPathInput.sendKeys(posterPath);
  }

  async getPosterPathInput() {
    return this.posterPathInput.getAttribute('value');
  }

  async setBackdropPathInput(backdropPath) {
    await this.backdropPathInput.sendKeys(backdropPath);
  }

  async getBackdropPathInput() {
    return this.backdropPathInput.getAttribute('value');
  }

  async setReleaseDateInput(releaseDate) {
    await this.releaseDateInput.sendKeys(releaseDate);
  }

  async getReleaseDateInput() {
    return this.releaseDateInput.getAttribute('value');
  }

  async voteSelectLastOption() {
    await this.voteSelect.all(by.tagName('option')).last().click();
  }

  async voteSelectOption(option) {
    await this.voteSelect.sendKeys(option);
  }

  getVoteSelect() {
    return this.voteSelect;
  }

  async getVoteSelectedOption() {
    return this.voteSelect.element(by.css('option:checked')).getText();
  }

  async companiesSelectLastOption() {
    await this.companiesSelect.all(by.tagName('option')).last().click();
  }

  async companiesSelectOption(option) {
    await this.companiesSelect.sendKeys(option);
  }

  getCompaniesSelect() {
    return this.companiesSelect;
  }

  async getCompaniesSelectedOption() {
    return this.companiesSelect.element(by.css('option:checked')).getText();
  }

  async countriesSelectLastOption() {
    await this.countriesSelect.all(by.tagName('option')).last().click();
  }

  async countriesSelectOption(option) {
    await this.countriesSelect.sendKeys(option);
  }

  getCountriesSelect() {
    return this.countriesSelect;
  }

  async getCountriesSelectedOption() {
    return this.countriesSelect.element(by.css('option:checked')).getText();
  }

  async genresSelectLastOption() {
    await this.genresSelect.all(by.tagName('option')).last().click();
  }

  async genresSelectOption(option) {
    await this.genresSelect.sendKeys(option);
  }

  getGenresSelect() {
    return this.genresSelect;
  }

  async getGenresSelectedOption() {
    return this.genresSelect.element(by.css('option:checked')).getText();
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
    await this.setTmdbIdInput('5');
    expect(await this.getTmdbIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setNameInput('name');
    expect(await this.getNameInput()).to.match(/name/);
    await waitUntilDisplayed(this.saveButton);
    await this.setTaglineInput('tagline');
    expect(await this.getTaglineInput()).to.match(/tagline/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDescriptionInput('description');
    expect(await this.getDescriptionInput()).to.match(/description/);
    await waitUntilDisplayed(this.saveButton);
    await this.setRevenueInput('5');
    expect(await this.getRevenueInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setBudgetInput('5');
    expect(await this.getBudgetInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setImdbIdInput('imdbId');
    expect(await this.getImdbIdInput()).to.match(/imdbId/);
    await waitUntilDisplayed(this.saveButton);
    await this.setPopularityInput('5');
    expect(await this.getPopularityInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setRuntimeInput('5');
    expect(await this.getRuntimeInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setPosterPathInput('posterPath');
    expect(await this.getPosterPathInput()).to.match(/posterPath/);
    await waitUntilDisplayed(this.saveButton);
    await this.setBackdropPathInput('backdropPath');
    expect(await this.getBackdropPathInput()).to.match(/backdropPath/);
    await waitUntilDisplayed(this.saveButton);
    await this.setReleaseDateInput('01-01-2001');
    expect(await this.getReleaseDateInput()).to.eq('2001-01-01');
    await this.voteSelectLastOption();
    // this.companiesSelectLastOption();
    // this.countriesSelectLastOption();
    // this.genresSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
