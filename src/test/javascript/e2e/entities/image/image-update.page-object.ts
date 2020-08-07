import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ImageUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.image.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  tmdbIdInput: ElementFinder = element(by.css('input#image-tmdbId'));
  aspectRatioInput: ElementFinder = element(by.css('input#image-aspectRatio'));
  filePathInput: ElementFinder = element(by.css('input#image-filePath'));
  heightInput: ElementFinder = element(by.css('input#image-height'));
  widthInput: ElementFinder = element(by.css('input#image-width'));
  iso6391Input: ElementFinder = element(by.css('input#image-iso6391'));
  voteSelect: ElementFinder = element(by.css('select#image-vote'));
  movieSelect: ElementFinder = element(by.css('select#image-movie'));
  personSelect: ElementFinder = element(by.css('select#image-person'));
  creditSelect: ElementFinder = element(by.css('select#image-credit'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTmdbIdInput(tmdbId) {
    await this.tmdbIdInput.sendKeys(tmdbId);
  }

  async getTmdbIdInput() {
    return this.tmdbIdInput.getAttribute('value');
  }

  async setAspectRatioInput(aspectRatio) {
    await this.aspectRatioInput.sendKeys(aspectRatio);
  }

  async getAspectRatioInput() {
    return this.aspectRatioInput.getAttribute('value');
  }

  async setFilePathInput(filePath) {
    await this.filePathInput.sendKeys(filePath);
  }

  async getFilePathInput() {
    return this.filePathInput.getAttribute('value');
  }

  async setHeightInput(height) {
    await this.heightInput.sendKeys(height);
  }

  async getHeightInput() {
    return this.heightInput.getAttribute('value');
  }

  async setWidthInput(width) {
    await this.widthInput.sendKeys(width);
  }

  async getWidthInput() {
    return this.widthInput.getAttribute('value');
  }

  async setIso6391Input(iso6391) {
    await this.iso6391Input.sendKeys(iso6391);
  }

  async getIso6391Input() {
    return this.iso6391Input.getAttribute('value');
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

  async movieSelectLastOption() {
    await this.movieSelect.all(by.tagName('option')).last().click();
  }

  async movieSelectOption(option) {
    await this.movieSelect.sendKeys(option);
  }

  getMovieSelect() {
    return this.movieSelect;
  }

  async getMovieSelectedOption() {
    return this.movieSelect.element(by.css('option:checked')).getText();
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

  async creditSelectLastOption() {
    await this.creditSelect.all(by.tagName('option')).last().click();
  }

  async creditSelectOption(option) {
    await this.creditSelect.sendKeys(option);
  }

  getCreditSelect() {
    return this.creditSelect;
  }

  async getCreditSelectedOption() {
    return this.creditSelect.element(by.css('option:checked')).getText();
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
    await this.setAspectRatioInput('5');
    expect(await this.getAspectRatioInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setFilePathInput('filePath');
    expect(await this.getFilePathInput()).to.match(/filePath/);
    await waitUntilDisplayed(this.saveButton);
    await this.setHeightInput('5');
    expect(await this.getHeightInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setWidthInput('5');
    expect(await this.getWidthInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setIso6391Input('iso6391');
    expect(await this.getIso6391Input()).to.match(/iso6391/);
    await this.voteSelectLastOption();
    await this.movieSelectLastOption();
    await this.personSelectLastOption();
    await this.creditSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
