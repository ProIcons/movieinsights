import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class VoteUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.vote.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  averageInput: ElementFinder = element(by.css('input#vote-average'));
  votesInput: ElementFinder = element(by.css('input#vote-votes'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setAverageInput(average) {
    await this.averageInput.sendKeys(average);
  }

  async getAverageInput() {
    return this.averageInput.getAttribute('value');
  }

  async setVotesInput(votes) {
    await this.votesInput.sendKeys(votes);
  }

  async getVotesInput() {
    return this.votesInput.getAttribute('value');
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
    await this.setAverageInput('5');
    expect(await this.getAverageInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setVotesInput('5');
    expect(await this.getVotesInput()).to.eq('5');
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
