/**
 * Simple logger system with the possibility of registering custom outputs.
 *
 * 4 different log levels are provided, with corresponding methods:
 * - debug   : for debug information
 * - info    : for informative status of the application (success, ...)
 * - warning : for non-critical errors that do not prevent normal application behavior
 * - error   : for critical errors that prevent normal application behavior
 *
 * Example usage:
 * ```
 * import { Logger } from 'app/core/logger.service';
 *
 * const log = new Logger('myFile');
 * ...
 * log.debug('something happened');
 * ```
 *
 * To disable debug and info logs in production, add this snippet to your root component:
 * ```
 * export class AppComponent implements OnInit {
 *   ngOnInit() {
 *     if (environment.production) {
 *       Logger.enableProductionMode();
 *     }
 *     ...
 *   }
 * }
 *
 * If you want to process logs through other outputs than console, you can add LogOutput functions to Logger.outputs.
 */

/**
 * The possible log levels.
 * LogLevel.Off is never emitted and only used with Logger.level property to disable logs.
 */
export enum LogLevel {
  Off = 0,
  Error,
  Warning,
  Info,
  Debug,
}

export type LogOutput = (
  source: string,
  level: LogLevel,
  ...objects: any[]
) => void;

export type LoggingFunc = (message: any, ...optionalParams: any[]) => void;

export class Logger {
  static level = LogLevel.Debug;

  private console: Console;

  static enableProductionMode() {
    Logger.level = LogLevel.Warning;
  }

  constructor(private source?: string, con: Console = console) {
    this.console = con;
  }

  debug(...params: any[]) {
    this.log(this.console.log, LogLevel.Debug, params);
  }

  info(...params: any[]) {
    this.log(this.console.info, LogLevel.Info, params);
  }

  warn(...params: any[]) {
    this.log(this.console.warn, LogLevel.Warning, params);
  }

  error(...params: any[]) {
    this.log(this.console.error, LogLevel.Error, params);
  }

  private log(func: LoggingFunc, level: LogLevel, params: any[]) {
    if (level <= Logger.level) {
      const log = this.source ? [`[${this.source}]`].concat(params) : params;
      func.apply(this.console, log);
    }
  }
}
