import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class MovieInsightsUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.movieInsights.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  averageRatingInput: ElementFinder = element(by.css('input#movie-insights-averageRating'));
  averageBudgetInput: ElementFinder = element(by.css('input#movie-insights-averageBudget'));
  averageRevenueInput: ElementFinder = element(by.css('input#movie-insights-averageRevenue'));
  totalMoviesInput: ElementFinder = element(by.css('input#movie-insights-totalMovies'));
  mostPopularGenreMovieCountInput: ElementFinder = element(by.css('input#movie-insights-mostPopularGenreMovieCount'));
  mostPopularActorMovieCountInput: ElementFinder = element(by.css('input#movie-insights-mostPopularActorMovieCount'));
  mostPopularWriterMovieCountInput: ElementFinder = element(by.css('input#movie-insights-mostPopularWriterMovieCount'));
  mostPopularProducerMovieCountInput: ElementFinder = element(by.css('input#movie-insights-mostPopularProducerMovieCount'));
  mostPopularDirectorMovieCountInput: ElementFinder = element(by.css('input#movie-insights-mostPopularDirectorMovieCount'));
  mostPopularProductionCompanyMovieCountInput: ElementFinder = element(
    by.css('input#movie-insights-mostPopularProductionCompanyMovieCount')
  );
  mostPopularProductionCountryMovieCountInput: ElementFinder = element(
    by.css('input#movie-insights-mostPopularProductionCountryMovieCount')
  );
  highestRatedMovieSelect: ElementFinder = element(by.css('select#movie-insights-highestRatedMovie'));
  lowestRatedMovieSelect: ElementFinder = element(by.css('select#movie-insights-lowestRatedMovie'));
  highestBudgetMovieSelect: ElementFinder = element(by.css('select#movie-insights-highestBudgetMovie'));
  lowestBudgetMovieSelect: ElementFinder = element(by.css('select#movie-insights-lowestBudgetMovie'));
  highestRevenueMovieSelect: ElementFinder = element(by.css('select#movie-insights-highestRevenueMovie'));
  lowestRevenueMovieSelect: ElementFinder = element(by.css('select#movie-insights-lowestRevenueMovie'));
  mostPopularGenreSelect: ElementFinder = element(by.css('select#movie-insights-mostPopularGenre'));
  mostPopularActorSelect: ElementFinder = element(by.css('select#movie-insights-mostPopularActor'));
  mostPopularProducerSelect: ElementFinder = element(by.css('select#movie-insights-mostPopularProducer'));
  mostPopularWriterSelect: ElementFinder = element(by.css('select#movie-insights-mostPopularWriter'));
  mostPopularDirectorSelect: ElementFinder = element(by.css('select#movie-insights-mostPopularDirector'));
  mostPopularProductionCountrySelect: ElementFinder = element(by.css('select#movie-insights-mostPopularProductionCountry'));
  mostPopularProductionCompanySelect: ElementFinder = element(by.css('select#movie-insights-mostPopularProductionCompany'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setAverageRatingInput(averageRating) {
    await this.averageRatingInput.sendKeys(averageRating);
  }

  async getAverageRatingInput() {
    return this.averageRatingInput.getAttribute('value');
  }

  async setAverageBudgetInput(averageBudget) {
    await this.averageBudgetInput.sendKeys(averageBudget);
  }

  async getAverageBudgetInput() {
    return this.averageBudgetInput.getAttribute('value');
  }

  async setAverageRevenueInput(averageRevenue) {
    await this.averageRevenueInput.sendKeys(averageRevenue);
  }

  async getAverageRevenueInput() {
    return this.averageRevenueInput.getAttribute('value');
  }

  async setTotalMoviesInput(totalMovies) {
    await this.totalMoviesInput.sendKeys(totalMovies);
  }

  async getTotalMoviesInput() {
    return this.totalMoviesInput.getAttribute('value');
  }

  async setMostPopularGenreMovieCountInput(mostPopularGenreMovieCount) {
    await this.mostPopularGenreMovieCountInput.sendKeys(mostPopularGenreMovieCount);
  }

  async getMostPopularGenreMovieCountInput() {
    return this.mostPopularGenreMovieCountInput.getAttribute('value');
  }

  async setMostPopularActorMovieCountInput(mostPopularActorMovieCount) {
    await this.mostPopularActorMovieCountInput.sendKeys(mostPopularActorMovieCount);
  }

  async getMostPopularActorMovieCountInput() {
    return this.mostPopularActorMovieCountInput.getAttribute('value');
  }

  async setMostPopularWriterMovieCountInput(mostPopularWriterMovieCount) {
    await this.mostPopularWriterMovieCountInput.sendKeys(mostPopularWriterMovieCount);
  }

  async getMostPopularWriterMovieCountInput() {
    return this.mostPopularWriterMovieCountInput.getAttribute('value');
  }

  async setMostPopularProducerMovieCountInput(mostPopularProducerMovieCount) {
    await this.mostPopularProducerMovieCountInput.sendKeys(mostPopularProducerMovieCount);
  }

  async getMostPopularProducerMovieCountInput() {
    return this.mostPopularProducerMovieCountInput.getAttribute('value');
  }

  async setMostPopularDirectorMovieCountInput(mostPopularDirectorMovieCount) {
    await this.mostPopularDirectorMovieCountInput.sendKeys(mostPopularDirectorMovieCount);
  }

  async getMostPopularDirectorMovieCountInput() {
    return this.mostPopularDirectorMovieCountInput.getAttribute('value');
  }

  async setMostPopularProductionCompanyMovieCountInput(mostPopularProductionCompanyMovieCount) {
    await this.mostPopularProductionCompanyMovieCountInput.sendKeys(mostPopularProductionCompanyMovieCount);
  }

  async getMostPopularProductionCompanyMovieCountInput() {
    return this.mostPopularProductionCompanyMovieCountInput.getAttribute('value');
  }

  async setMostPopularProductionCountryMovieCountInput(mostPopularProductionCountryMovieCount) {
    await this.mostPopularProductionCountryMovieCountInput.sendKeys(mostPopularProductionCountryMovieCount);
  }

  async getMostPopularProductionCountryMovieCountInput() {
    return this.mostPopularProductionCountryMovieCountInput.getAttribute('value');
  }

  async highestRatedMovieSelectLastOption() {
    await this.highestRatedMovieSelect.all(by.tagName('option')).last().click();
  }

  async highestRatedMovieSelectOption(option) {
    await this.highestRatedMovieSelect.sendKeys(option);
  }

  getHighestRatedMovieSelect() {
    return this.highestRatedMovieSelect;
  }

  async getHighestRatedMovieSelectedOption() {
    return this.highestRatedMovieSelect.element(by.css('option:checked')).getText();
  }

  async lowestRatedMovieSelectLastOption() {
    await this.lowestRatedMovieSelect.all(by.tagName('option')).last().click();
  }

  async lowestRatedMovieSelectOption(option) {
    await this.lowestRatedMovieSelect.sendKeys(option);
  }

  getLowestRatedMovieSelect() {
    return this.lowestRatedMovieSelect;
  }

  async getLowestRatedMovieSelectedOption() {
    return this.lowestRatedMovieSelect.element(by.css('option:checked')).getText();
  }

  async highestBudgetMovieSelectLastOption() {
    await this.highestBudgetMovieSelect.all(by.tagName('option')).last().click();
  }

  async highestBudgetMovieSelectOption(option) {
    await this.highestBudgetMovieSelect.sendKeys(option);
  }

  getHighestBudgetMovieSelect() {
    return this.highestBudgetMovieSelect;
  }

  async getHighestBudgetMovieSelectedOption() {
    return this.highestBudgetMovieSelect.element(by.css('option:checked')).getText();
  }

  async lowestBudgetMovieSelectLastOption() {
    await this.lowestBudgetMovieSelect.all(by.tagName('option')).last().click();
  }

  async lowestBudgetMovieSelectOption(option) {
    await this.lowestBudgetMovieSelect.sendKeys(option);
  }

  getLowestBudgetMovieSelect() {
    return this.lowestBudgetMovieSelect;
  }

  async getLowestBudgetMovieSelectedOption() {
    return this.lowestBudgetMovieSelect.element(by.css('option:checked')).getText();
  }

  async highestRevenueMovieSelectLastOption() {
    await this.highestRevenueMovieSelect.all(by.tagName('option')).last().click();
  }

  async highestRevenueMovieSelectOption(option) {
    await this.highestRevenueMovieSelect.sendKeys(option);
  }

  getHighestRevenueMovieSelect() {
    return this.highestRevenueMovieSelect;
  }

  async getHighestRevenueMovieSelectedOption() {
    return this.highestRevenueMovieSelect.element(by.css('option:checked')).getText();
  }

  async lowestRevenueMovieSelectLastOption() {
    await this.lowestRevenueMovieSelect.all(by.tagName('option')).last().click();
  }

  async lowestRevenueMovieSelectOption(option) {
    await this.lowestRevenueMovieSelect.sendKeys(option);
  }

  getLowestRevenueMovieSelect() {
    return this.lowestRevenueMovieSelect;
  }

  async getLowestRevenueMovieSelectedOption() {
    return this.lowestRevenueMovieSelect.element(by.css('option:checked')).getText();
  }

  async mostPopularGenreSelectLastOption() {
    await this.mostPopularGenreSelect.all(by.tagName('option')).last().click();
  }

  async mostPopularGenreSelectOption(option) {
    await this.mostPopularGenreSelect.sendKeys(option);
  }

  getMostPopularGenreSelect() {
    return this.mostPopularGenreSelect;
  }

  async getMostPopularGenreSelectedOption() {
    return this.mostPopularGenreSelect.element(by.css('option:checked')).getText();
  }

  async mostPopularActorSelectLastOption() {
    await this.mostPopularActorSelect.all(by.tagName('option')).last().click();
  }

  async mostPopularActorSelectOption(option) {
    await this.mostPopularActorSelect.sendKeys(option);
  }

  getMostPopularActorSelect() {
    return this.mostPopularActorSelect;
  }

  async getMostPopularActorSelectedOption() {
    return this.mostPopularActorSelect.element(by.css('option:checked')).getText();
  }

  async mostPopularProducerSelectLastOption() {
    await this.mostPopularProducerSelect.all(by.tagName('option')).last().click();
  }

  async mostPopularProducerSelectOption(option) {
    await this.mostPopularProducerSelect.sendKeys(option);
  }

  getMostPopularProducerSelect() {
    return this.mostPopularProducerSelect;
  }

  async getMostPopularProducerSelectedOption() {
    return this.mostPopularProducerSelect.element(by.css('option:checked')).getText();
  }

  async mostPopularWriterSelectLastOption() {
    await this.mostPopularWriterSelect.all(by.tagName('option')).last().click();
  }

  async mostPopularWriterSelectOption(option) {
    await this.mostPopularWriterSelect.sendKeys(option);
  }

  getMostPopularWriterSelect() {
    return this.mostPopularWriterSelect;
  }

  async getMostPopularWriterSelectedOption() {
    return this.mostPopularWriterSelect.element(by.css('option:checked')).getText();
  }

  async mostPopularDirectorSelectLastOption() {
    await this.mostPopularDirectorSelect.all(by.tagName('option')).last().click();
  }

  async mostPopularDirectorSelectOption(option) {
    await this.mostPopularDirectorSelect.sendKeys(option);
  }

  getMostPopularDirectorSelect() {
    return this.mostPopularDirectorSelect;
  }

  async getMostPopularDirectorSelectedOption() {
    return this.mostPopularDirectorSelect.element(by.css('option:checked')).getText();
  }

  async mostPopularProductionCountrySelectLastOption() {
    await this.mostPopularProductionCountrySelect.all(by.tagName('option')).last().click();
  }

  async mostPopularProductionCountrySelectOption(option) {
    await this.mostPopularProductionCountrySelect.sendKeys(option);
  }

  getMostPopularProductionCountrySelect() {
    return this.mostPopularProductionCountrySelect;
  }

  async getMostPopularProductionCountrySelectedOption() {
    return this.mostPopularProductionCountrySelect.element(by.css('option:checked')).getText();
  }

  async mostPopularProductionCompanySelectLastOption() {
    await this.mostPopularProductionCompanySelect.all(by.tagName('option')).last().click();
  }

  async mostPopularProductionCompanySelectOption(option) {
    await this.mostPopularProductionCompanySelect.sendKeys(option);
  }

  getMostPopularProductionCompanySelect() {
    return this.mostPopularProductionCompanySelect;
  }

  async getMostPopularProductionCompanySelectedOption() {
    return this.mostPopularProductionCompanySelect.element(by.css('option:checked')).getText();
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
    await this.setAverageRatingInput('5');
    expect(await this.getAverageRatingInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setAverageBudgetInput('5');
    expect(await this.getAverageBudgetInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setAverageRevenueInput('5');
    expect(await this.getAverageRevenueInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setTotalMoviesInput('5');
    expect(await this.getTotalMoviesInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setMostPopularGenreMovieCountInput('5');
    expect(await this.getMostPopularGenreMovieCountInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setMostPopularActorMovieCountInput('5');
    expect(await this.getMostPopularActorMovieCountInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setMostPopularWriterMovieCountInput('5');
    expect(await this.getMostPopularWriterMovieCountInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setMostPopularProducerMovieCountInput('5');
    expect(await this.getMostPopularProducerMovieCountInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setMostPopularDirectorMovieCountInput('5');
    expect(await this.getMostPopularDirectorMovieCountInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setMostPopularProductionCompanyMovieCountInput('5');
    expect(await this.getMostPopularProductionCompanyMovieCountInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setMostPopularProductionCountryMovieCountInput('5');
    expect(await this.getMostPopularProductionCountryMovieCountInput()).to.eq('5');
    await this.highestRatedMovieSelectLastOption();
    await this.lowestRatedMovieSelectLastOption();
    await this.highestBudgetMovieSelectLastOption();
    await this.lowestBudgetMovieSelectLastOption();
    await this.highestRevenueMovieSelectLastOption();
    await this.lowestRevenueMovieSelectLastOption();
    await this.mostPopularGenreSelectLastOption();
    await this.mostPopularActorSelectLastOption();
    await this.mostPopularProducerSelectLastOption();
    await this.mostPopularWriterSelectLastOption();
    await this.mostPopularDirectorSelectLastOption();
    await this.mostPopularProductionCountrySelectLastOption();
    await this.mostPopularProductionCompanySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
