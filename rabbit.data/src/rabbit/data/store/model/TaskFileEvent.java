/*
 * Copyright 2010 The Rabbit Eclipse Plug-in Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package rabbit.data.store.model;


import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.mylyn.tasks.core.ITask;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;

/**
 * Represents a task event.
 */
public class TaskFileEvent extends FileEvent {

  @Nonnull
  private final ITask task;

  /**
   * Constructs a new event.
   * 
   * @param endTime The end time of the event.
   * @param duration The duration of the event, in milliseconds.
   * @param fileId The id of the file.
   * @param task The task that was working on.
   * @throws IllegalArgumentException If duration is negative, and/or the fileId
   *           is an empty string or contains white space.
   * @throws NullPointerException If time is null, or file id is null, or task
   *           is null.
   */
  public TaskFileEvent(@Nonnull DateTime endTime, 
                                long duration, 
                       @Nonnull String fileId, 
                       @Nonnull ITask task) {
    
    super(endTime, duration, fileId);
    checkNotNull(task);
    this.task = task;
  }

  /**
   * Gets the task.
   * 
   * @return The task.
   */
  @Nonnull
  public ITask getTask() {
    return task;
  }
}